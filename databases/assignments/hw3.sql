-- Matthew OBrien
-- CS5200 HW3
-- Summer 2019


-- PART 1: Create Database

drop database if exists hw3;
create database hw3;
use hw3;

drop table if exists users;

create table users (
	user_id int primary key auto_increment,
    handle varchar(20) unique not null,
    username varchar(20) not null,
    email varchar(30) unique not null,
    pass varchar(30) not null,
    private boolean not null default false,
    acct_type ENUM('person', 'organization') not null default 'person'
);

drop table if exists nests;

create table nests (
    nest_id int primary key auto_increment,
    nest_name varchar(50) unique not null
);

drop table if exists hashtags;

create table hashtags (
	hashtag_id int primary key auto_increment,
	hashtag varchar(159) unique not null
);

drop table if exists tweets;

create table tweets (
	tweet_id int primary key auto_increment,
    tweeter int not null,
	constraint tweeter_fk foreign key (tweeter) references users (user_id),
	broadcast boolean not null default true,
    to_nest int,
    constraint nest_fk foreign key (to_nest) references nests (nest_id),
	message varchar(160) not null,
	time_stamp datetime not null
);

drop table if exists user_follows;

create table user_follows (
	follower int not null,
    constraint follower_fk foreign key (follower) references users (user_id),
    followed int not null,
    constraint followed_fk foreign key (followed) references users (user_id)
);

drop table if exists nest_memberships;

create table nest_memberships (
	nest_id int not null,
    constraint nest_id_fk foreign key (nest_id) references nests (nest_id),
    user_id int not null,
    constraint nest_user_id_fk foreign key (user_id) references users (user_id)
);

drop table if exists likes;

create table likes (
	liker_id int not null,
    constraint liker_id_fk foreign key (liker_id) references users (user_id),
    tweet_id int not null,
    constraint tweet_id_fk foreign key (tweet_id) references tweets (tweet_id)
    );
    
drop table if exists tweet_hashtags;

create table tweet_hashtags (
	tweet_id int not null,
    constraint tweet_hashtag_fk foreign key (tweet_id) references tweets (tweet_id),
    tweet_hashtag int not null,
    constraint hashtag_fk foreign key (tweet_hashtag) references hashtags (hashtag_id)
);


-- PART 2: Populate Tables with Data

insert into users (handle, username, email, pass, private, acct_type)
	values
	('mobrien', 'Matthew OBrien', 'obrien.matt@husky.neu.edu', 'greatpassword1', false, 'person'),
    ('cnn', 'CNN News', 'cnn@cnn.com', 'password', false, 'organization'),
    ('wwhite', 'Walter White', 'wwhite@sci.org', 'imakemeth', false, 'person'),
    ('heisenberg', 'Heisenberg', 'h@m.com', 'nodeal', true, 'person'),
    ('stringer', 'Stringer Bell', 'str@gmail.com', 'tothetop', true, 'person'),
    ('whitewalkers', 'The White Walkers', 'whitewalkers@gmail.com', 'shouldvewon', false, 'organization'),
    ('arya', 'Arya Stark', 'arya@winterfell.com', 'agirlneedsnopassword', false, 'person'),
    ('sansa', 'Sansa Stark', 'sansa@winterfell.com', 'queeninthenorth', false, 'person'),
    ('dany', 'Deanarys Targeareon', 'dany@dragons.com', 'imactuallyinsane', false, 'person'),
    ('dragons', 'Danys Dragons', '3dragons@dragons.com', 'represent', false, 'organization');
    
insert into nests (nest_name)
	values
	('casual'),
    ('got'),
    ('breaking bad'),
    ('sports'),
    ('northeastern'),
    ('news'),
    ('the north'),
    ('crimestoppers'),
    ('whiskey fans'),
    ('nonhumans');
    
insert into user_follows
	values
    (1,7),
    (1,5),
    (10,9),
    (3,4),
    (7,8),
    (5,7),
    (9,7),
    (6,7),
    (8,1),
    (2,6),
    (4,1);
    
insert into tweets (tweeter, broadcast, to_nest, message, time_stamp)
	values
    (1, true, null, 'testing 123', now()),
    (9, false, 2, 'where my #dragons at', now()),
    (7, false, 2, 'screw you and your #dragons #north4eva', now()),
    (3, true, null, 'hey guys were back', now()),
    (2, false, 6, 'the #whitewalkers have returned!', now()),
    (5, true, null, 'lets team up #dreamteam #whitewalkers #heisenberg', now()),
    (4, true, null, 'im in', now()),
    (8, true, null, 'no sweat #northernalliance #gotthis', now()),
    (7, true, null, '#werd sister', now()),
    (2, false, 6, 'looks like a big battle ahead!', now());
    
insert into nest_memberships
	values
    (2,7),
    (2,8),
    (2,9),
    (2,10),
    (2,6),
    (3,4),
    (6,2),
    (7,7),
    (8,4),
    (9,10);
    
insert into hashtags (hashtag)
	values
    ('dragons'),
    ('north4eva'),
    ('whitewalkers'),
    ('dreamteam'),
    ('heisenberg'),
    ('northernalliance'),
    ('gotthis'),
    ('werd');
    
insert into tweet_hashtags
	values
    (2, 1),
    (3, 1),
    (3, 2),
    (5, 3),
    (6, 4),
    (6, 3),
    (6, 5),
    (8, 6),
    (8, 7),
    (9, 8);
    
insert into likes
	values
    (7,8),
    (10,2),
    (8,3),
    (8,9),
    (6,5),
    (3,7),
    (4,6),
    (6,6),
    (7,10),
    (3,10);


-- PART 3: Database Validation

-- a)
select handle, count(follower) as followers					-- sets columns on return
from (
	select follower, followed
    from user_follows fo
		inner join users us on (fo.follower = us.user_id)
	where us.private = false
	) f 													-- only selects follower/followed where follower is public
    right join users u on (u.user_id = f.followed) 			-- will return all users, even w/o followers
group by handle 											-- groups by handle
order by followers desc; 									-- sets descending order

-- b)
select handle, message, time_stamp						-- select output columns
from tweets t
	join users u on (u.user_id = t.tweeter) 			-- link tweeter to their handle
where tweeter in (
	select followed
    from user_follows
    where follower = 1 -- mobrien's home timeline
    ) 													-- filter by desired user's timeline
    and to_nest is null 								-- ensure tweets are broadcast
order by time_stamp desc 								-- order by most recent
limit 5; 												-- only show 5 most recent

-- c)
select hashtag, count(*) as tweets						-- sets return columns
from hashtags h											-- source table: hashtags
	right join (
		select tweet_hashtag
        from tweet_hashtags
			left join tweets using (tweet_id)
		where time_stamp > (now() - 00003000000000)
        ) th on (h.hashtag_id = th.tweet_hashtag) 		-- select within date range: 30 days; match to hashtag text
group by hashtag 										-- group by hashtag
order by tweets desc; 									-- orders by num of tweets

-- d)
select count(M) as N, M									-- set return columns
from (
	select nest_id, count(user_id) as M
	from nest_memberships
	group by nest_id
    ) tmp 												-- get number of members per nest
group by M 												-- group by number of nests w/ same number of members
order by M asc; 										-- order by number of members ascending

-- e)
select nest_name, count(*) as num_tweets 				-- set return columns
from nests n
	left join tweets t on (n.nest_id = t.to_nest)		-- join nests with tweets according to nest of tweet (will also exclude broadcast tweets)
	join tweet_hashtags th using (tweet_id)  			-- join to hashtags according to tweet
where th.tweet_hashtag = 1 								-- select desired hashtag (here: 'dragons')
group by nest_name 										-- group by nest name
order by num_tweets desc; 								-- set descending order
