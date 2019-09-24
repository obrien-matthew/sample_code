-- CS 3200/5200
-- QUIZ 3: SQL

-- Read each question carefully
-- Write a single query to answer the question

-- Do NOT use views!
-- Do NOT hard-code primary key values or keys in WHERE clauses. Assume the data could change!
-- Format your query for readability (lowercase is fine, but use multiple lines)
-- Check your work 
--    Does the output make sense? 
--    Does it include all the requested columns? 
--    Is the data sorted correctly?

-- Save your file as quiz3_yourname.sql
-- SAVE YOUR FILE FREQUENTLY!

-- Questions 1-10: 	8 points each
-- Questions 11-12: 	10 points each



use squirrels;
show tables;
select * from behavior;
select * from observation;
select * from observer;
select * from species;
select * from squirrel;

-- The problem:
-- Animal researchers are studying a family of squirrels known as the Walnuts. 
-- The squirrels live on Walnut mountain.
-- They've catalogued their observations into a database
-- It's time to analyze some of their results.....

-- 1. How many male and female squirrels are in the database?
select gender, count(*) as number
from squirrel
group by gender
order by number desc;


-- 2. How many total observations were catalogued by each observer?
-- Include observers that made no observations
-- Output the name of the observer and the number of observations
-- sorted in descending order by number of observations
-- Give the observation count a meaningful column name
select name, count(n.observer_id) as total_observations
from observer o
	left join observation n using (observer_id)
group by name
order by total_observations desc;


-- 3. How many observations are there for each squirrel?
-- Output the name of the squirrel and the number of observations
-- Include squirrels with no observations
-- Assign descriptive column headers
-- Sort results from most-observed to least-observed squirrel.
select name, count(o.squirrel_id) as times_observed
from squirrel s
	left join observation o using (squirrel_id)
group by name
order by times_observed desc;


-- 4. On average, how many observations were made per squirrel?
-- Make sure you factor in squirrels with zero observations!
select avg(times_observed) as avg_observations_per_squirrel
from (
	select name, count(o.squirrel_id) as times_observed
	from squirrel s
		left join observation o using (squirrel_id)
	group by name
	order by times_observed desc
    ) count;



-- 5. How many observations were made in each Month and Year
-- you can ignore months where no observations were made
-- Use the YEAR() and MONTH() functions to extract year and month from the observation date.
-- Sort by the year and the month of the observation
select month(observation_date) as M, year(observation_date) as Y, count(*) as num_observations
from observation
group by Y, M
order by Y, M asc;


-- 6. Which two squirrels were observed fighting the most (not necessarily with each other)?
-- Output the names of the two squirrels that fight the most, their gender
-- and the number of times each was observed to be fighting
select name, gender, count(o.squirrel_id) as times_fighting
from observation o
	left join squirrel s using (squirrel_id)
	join behavior b using (behavior_id)
where b.description = 'fighting'
group by name, gender
order by times_fighting desc
limit 2;



-- -----------------------------------------------------------------------------------------------------------
-- JOKE (Not a test question!):
-- A police officer sees a man driving around with a pickup truck full of Squirrels. 
-- He pulls the guy over and says... "You can't drive around with squirrels in this town! 
-- Take them to the zoo immediately." The guy says "OK"... and drives away. 
-- The next day, the officer sees the guy still driving around with the truck full of 
-- squirrels, and they're all wearing sun glasses. He pulls the guy over and demands... 
-- "I thought I told you to take these squirrels to the zoo yesterday?" The guy replies... 
-- "I did . . . today I'm taking them to the beach!" 
-- -----------------------------------------------------------------------------------------------------------



-- 7. Which squirrels were observed LESS than average - that is, the number of observations of that squirrel
--  is less than the average number of observations per squirrel.
-- Don't hardcode the average - compute it within your query
-- Output the name of the squirrel and the actual number of times it was observed
-- Include squirrels having no observations
-- Sort your output from least observed to most observed, and by name in case of ties
select name, count(o.squirrel_id) as times_observed
from squirrel s
	left join observation o using (squirrel_id)
group by name
having times_observed < (
	select count(s.squirrel_id)/count(o.observation_id) as average
    from squirrel s
		left join observation o using (squirrel_id)
	)
order by times_observed, name asc;



-- 8. For Northern flying squirrels only:
-- how many times each behavior was observed?
-- Include behaviors never observered with Northern flying squirrels.
-- Output the behavior description and the number of times that behavior was observed
-- Order by the number of observations in descending order
select description, sum(case when common_name = 'Northern flying squirrel' then 1 else 0 end) as times_observed
from behavior b
	left join observation o using (behavior_id)
    left join squirrel s using (squirrel_id)
    left join species p using (species_id)
group by description
order by times_observed desc;


-- 9. Give the name of each squirrel, it's gender,  the name of it's mother and the mother's gender (hopefully 'F'!)
-- Include squirrels that have no known mother
-- Sort output by the mother's name
select s.name as name, s.gender as gender, m.name as mother_name, m.gender as mother_gender
from squirrel s
	left join squirrel m on (s.mother_id = m.squirrel_id)
order by mother_name asc;




-- 10. Which 3 squirrels are the most adorable?  (Defined as
-- being observed 'being generally adorable' the most number of times).
-- Output ONLY the name of the THREE most adorable squirrels and the number of times
-- each was observed 'being generally adorable"
-- assign the count column a more meaningful name
-- sort from most adorable to least adorable
select name, count(o.squirrel_id) as times_adorable
from squirrel s
	left join observation o using (squirrel_id)
    left join behavior b using (behavior_id)
where description = 'Being generally adorable'
group by name
order by times_adorable desc
limit 3;


-- 11. How many observations of Eastern Gray Squirrels where made by each observer?
-- Only include observers that made 10 or more observations of Eastern Gray Squirrels
-- Do NOT, however, count observations where the listed behavior was 'Being generally adorable' 
-- or 'Eyeing observer suspiciously' because those descriptions are just plain nutty.
-- Output the name of the observer, and the number of observations
-- Sort results in descending order by number of observations
select r.name as observer_name, count(o.observer_id) as number_observations
from observation o
	join observer r using (observer_id)
    join behavior b using (behavior_id)
    join squirrel s using (squirrel_id)
    join species sp using (species_id)
where description <> 'being generally adorable'
	and description <> 'eyeing observer suspiciously'
    and common_name = 'eastern gray squirrel'
group by observer_name
having number_observations >= 10
order by number_observations desc;



-- 12. Do male or female squirrels fight more?
-- Compute the average number of observations of fighting per squirrel broken down by gender
-- Factor into your average squirrels that were never observed to be fighting.
-- Output the gender and the average fight observations per squirrel (for squirrels of that gender).

-- Hint: Start by counting how often each squirrel was observed fighting
-- including squirrels never observed fighting
select gender, avg(times_fighting) as avg_fights
from (
	select gender, sum(case when b.description = 'fighting' then 1 else 0 end) as times_fighting
	from squirrel s
		left join observation o using (squirrel_id)
		join behavior b using (behavior_id)
	group by name, gender
	) fights
group by gender
order by avg_fights desc;


-- That's it, you're done!
-- Save this file as quiz3_YourName.sql
-- and post it as the answer to the one and only quiz question.


