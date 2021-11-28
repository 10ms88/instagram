select username, biography, category, follower_count, following_count, media_count, usertags_count
from bot_user
where following_count > follower_count
  and following_count > 1000
order by media_count
