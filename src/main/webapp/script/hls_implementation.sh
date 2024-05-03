# 1 -> Input File Name
# 2 -> Output Folder
# 3 -> Unique Video ID
# 4 -> Video Resolution     === 1920x1080        1280x720      854x480
# 5 -> Video Bitrate        === 5000k            2500k         1000k
# 6 -> Audio Bitrate        === 192k             128k          96k
# 7 -> Resolution Name      === 1080p            720p          480p
input_file=$1
output_folder=$2$3'/'
uuid=$3
video_scale=$4
video_bitrate=$5
audio_bitrate=$6
resolution_name=$7
output_file=$output_folder$resolution_name'/'$uuid
create_movie_folder=$output_folder$resolution_name

echo "Input file: $input_file"
echo "Output file: $output_file.mp4"

ffmpeg -loglevel warning -i $input_file \
       -c:v libx264 -preset veryfast -tune film \
       -b:v $video_bitrate -vf "scale=$video_scale" \
       -c:a aac -b:a $audio_bitrate \
       $output_file'.mp4'

ffmpeg -loglevel warning -i $output_file'.mp4' -c:v copy -c:a copy \
       -hls_time 10 -hls_list_size 0 -hls_flags delete_segments \
       -hls_segment_filename $output_file'%03d.ts' -f hls $output_file'.m3u8'

rm $output_file'.mp4'

if ! test -f $output_folder'master.m3u8'; then
	echo "#EXTM3U" >> $output_folder'master.m3u8'
fi
	
echo "#EXT-X-STREAM-INF:BANDWIDTH=$video_bitrate,RESOLUTION=$video_scale
$resolution_name/$uuid.m3u8" >> $output_folder'master.m3u8'
