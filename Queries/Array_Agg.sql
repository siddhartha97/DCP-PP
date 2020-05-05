with table1 as (select * , split(trim(created_at, "\""), " ") as time_stamp FROM `dcp-pp.twitter_dataset.table2`)
, processed_time_stamp as (select * except(time_stamp), time_stamp[offset(0)] as day, time_stamp[offset(2)] as date,time_stamp[offset(1)] as month, time_stamp[offset(3)] as time, time_stamp[offset(5)] as year from table1)
,entire_table as (select * from `dcp-pp.twitter_dataset.table2`)
,unique_table as (select* from (select *, row_number() over (partition by user_id) row_idx from processed_time_stamp) where row_idx = 1 
),follower_table as (select country_code, array_agg(struct(user_followers, user_following)) as user from unique_table where country_code != "" group by 1)

-- Array_Aggregate1
select * from follower_table
-- Array_Aggregate2
select year, array_agg(STRUCT(country_code, users)) as str from (select year, country_code , count(distinct user_id)as users from unique_table where country_code != "" group by 1,2 order by 1) group by year
