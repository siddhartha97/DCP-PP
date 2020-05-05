create temp function
solve(param ARRAY<STRUCT<user_followers INT64, user_following INT64>>)
  returns STRUCT<avg_follower INT64, avg_following INT64>
  language js as """
  var len = param.length;
  var following_ct = 0;
  var follower_ct = 0;
  var ct = 0;
  for(var i = 0; i < len; ++i) {
    //follower_ct = param.user[i].user_followers;
    var t1 = parseInt(param[i].user_followers)
    var t2 = parseInt(param[i].user_following);
    follower_ct = follower_ct + t1
    following_ct = following_ct + t2;
  }
  return {avg_follower : follower_ct/len, avg_following : following_ct/len}
""";
with table1 as (select * , split(trim(created_at, "\""), " ") as time_stamp FROM `dcp-pp.twitter_dataset.table2`)
, processed_time_stamp as (select * except(time_stamp), time_stamp[offset(0)] as day, time_stamp[offset(2)] as date,time_stamp[offset(1)] as month, time_stamp[offset(3)] as time, time_stamp[offset(5)] as year from table1)
,entire_table as (select * from `dcp-pp.twitter_dataset.table2`)
,unique_table as (select* from (select *, row_number() over (partition by user_id) row_idx from processed_time_stamp) where row_idx = 1 
),follower_table as (select country_code, array_agg(struct(user_followers, user_following)) as user from unique_table where country_code != "" group by 1)

select country_code, solve(user) from follower_table order by country_code desc
