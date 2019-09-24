-- CS5200 HW4: Books that will change your life
-- Instructions: Run the script "hw4_library_setup.sql" in a ROOT connection
-- This will create a new schema called "library"
-- Write a query that answers each question below.
-- Save this file as HW4_YourFullName.sql and submit to Blackboard

-- Questions 1-12 are 8 points each. Question 13 is worth 4 points.


use library;
select * from genre;


-- 1. Which book(s) are Science Fiction books written in the 1960's?
-- List title, author, and year of publication

select title, author, year as year_of_pub 
from book
	left join genre using (genre_id)
where genre_name like 'Science Fiction' and year between 1959 and 1971
order by title;



-- 2. Which users have borrowed no books?
-- Give name and city they live in
-- Write the query in two ways, once by selecting from only one table
-- and using a subquery, and again by joining two tables together.


-- Method using subquery (4 points)
select user_name, city
from user
where user_id not in (
	select distinct user_id
    from borrow
    );

-- Method using a join (4 points)
select user_name, city
from user u
	left join borrow b using (user_id)
group by user_name, city
having count(b.user_id) = 0;



-- 3. How many books were borrowed by each user in each month?
-- Your table should have three columns: user_name, month, num_borrowed
-- You may ignore users that didn't borrow any books and months in which no books were borrowed.
-- Sort by name, then month
-- The month(date) function returns the month number (1,2,3,...12) of a given date. This is adequate for output.

select u.user_name, month(b.borrow_dt) as month, count(b.user_id) as num_borrowed
from borrow b
	left join user u using (user_id)
group by u.user_name, month
order by u.user_name, month asc;



-- 4. How many times was each book checked out?
-- Output the book's title, genre name, and the number of times it was checked out, and whether the book is still in circulation
-- Include books never borrowed
-- Order from most borrowed to least borrowed

select b.title, g.genre_name, count(bw.book_id) as checked_out, b.in_circulation
from book b
	left join genre g using (genre_id)
    left join borrow bw using (book_id)
group by b.title, g.genre_name, b.in_circulation
order by checked_out desc;


-- 5. How many times did each user return a book late?
-- Include users that never returned a book late or never even borrowed a book
-- Sort by most number of late returns to least number of late returns (regardless of HOW late the returns were.)

select u.user_name, sum(case when b.due_dt < b.return_dt then 1 else 0 end) as late_returns
from user u
	left join borrow b using (user_id)
group by u.user_name
order by late_returns desc;



-- 6. How many books of each genre where published after 1950?
-- Include genres that are not represented by any book in our catalog
-- as well as genres for which there are books but none published after 1950.
-- Sort output by number of titles in each genre (most to least)

select genre_name, sum(case when year > 1950 then 1 else 0 end) as num_books_post_1950
from genre
	left join book using (genre_id)
group by genre_name
order by num_books_post_1950 desc;


-- 7. For each genre, compute a) the number of books borrowed and b) the average
-- number of days borrowed. 
-- Includes books never borrowed and genres with no books
-- and in these cases, show zeros instead of null values.
-- Round the averages to one decimal point
-- Sort output in descending order by average
-- Helpful functions: ROUND, IFNULL, DATEDIFF
select g.genre_name, count(bw.book_id) as number_borrowed, round(ifnull(avg(datediff(bw.return_dt,bw.borrow_dt)),0),1) as average_days_out
from borrow bw
	join book bk using (book_id)
	right join genre g using (genre_id)
group by g.genre_name
order by average_days_out desc;


-- 8. List all pairs of books published within 10 years of each other
-- Don't include the book with itself
-- Only list (X,Y) pairs where X was published earlier
-- Output the two titles, and the years they were published, the number of years apart they were published
-- Order pairs from those published closest together to farthest

select b1.title as older_title, b1.year as older_year, b2.title as younger_title, b2.year as younger_year, b2.year-b1.year as difference
from book b1
	join book b2
where b1.year < b2.year and b1.year+10>b2.year
order by difference asc;




-- 9. Assuming books are returned completely read,
-- Rank the users from fastest to slowest readers (pages per day)
-- include users that borrowed no books (report reading rate as 0.0)
select user_name, round(ifnull(avg(pages/datediff(return_dt,borrow_dt)),0),1) as reading_rate
from user
	left join borrow using (user_id)
	left join book using (book_id)
group by user_name
order by reading_rate desc;



-- 10. How many books of each genre were checked out by John?
-- Sort descending by number of books checked out in each genre category.
-- Only include genres where at least two books of that genre were checked out.  
-- (Count each time the book was checked out even if the same book was checked out
-- by John more than once.)
select genre_name, count(*) as num_books
from genre
	join book using (genre_id)
    join borrow using (book_id)
    join user using (user_id)
where user_name = 'John'
group by genre_name
having num_books > 1
order by num_books desc;


-- 11. On average how many books are borrowed per user?
-- Output two averages in one row: one average that includes users that
-- borrowed no books, and one average that excludes users that borrowed no books
  
select count(bw.book_id)/count(distinct u.user_id) as avg_incl, count(bw.book_id)/count(distinct bw.user_id) as avg_excl
from borrow bw
	right join user u using (user_id);



-- 12. How much does each user owe the library. Include users owing nothing
-- Factor in the 10 cents per day fine for late returns and how much they have already paid the library
-- HINTS: 
--     The DATEDIFF function takes two dates and counts the number of dates between them
--     The IF function, used in a SELECT clause, might also be helpful.  IF(condition, result_if_true, result_if_false)
--     IF functions can be used inside aggregation functions!

select user_name, sum(amount) as amount_due
from (
	select user_name, if(datediff(return_dt,due_dt)>0,datediff(return_dt,due_dt)*.10,0) as amount
	from user
		left join borrow using (user_id)
	union
	select user_name, ifnull(amount*-1,0) as amount
	from user
		right join payment using (user_id)
	) amt
group by user_name
order by amount_due desc;


-- 13. (4 points) Which books will change your life?
-- Answer: All books. 
-- Select all books.

select * from book;



