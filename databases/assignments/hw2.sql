-- CS5200: Database Systems
-- Homework #2: Genes and Disease

-- Create a new schema called "gad"
-- Then use the data import wizard to load the data "gad.csv" into a new table, also called "gad"
-- Remember to change the inferred datatype for the chromosome field from INT to TEXT
-- (There is a MySQL workbench bug where you have to slightly resize the dialog box before you can scroll through the list of fields.)
-- You should end up with a table containing 39,910 records

-- Write a query to answer each of the following questions
-- Questions 1-15: 6 points each. Question 16: 10 points.
-- Save your script file as cs5200_hw2_fullname.sql
-- Submit this file for your homework submission


use gad;
-- describe gad;

-- 1. 
-- Verify that you have 39,910 records 

select count(*)
from gad;

-- 2. 
-- What are the most studied disease classes?
-- In otherwords, how many gad records are there for each disease class?
-- Output your list from most records to least

select disease_class, count(*) as num_records
from gad
group by disease_class
order by num_records desc;

-- 3. 
-- What are the most studied phenotypes for the disease class IMMUNE?

select phenotype, count(*) as num_records
from gad
where disease_class = 'IMMUNE'
group by phenotype
order by num_records desc
limit 5;


-- 4. 
-- List all G protein-coupled receptors in alphabetical order
-- Hint: look for the phrase "G protein-coupled" in the gene name.
-- Output gene, gene name, and chromosome
-- (These genes are often the target for new drugs, so are of particular interest)

select gene, gene_name, chromosome
from gad
where gene_name like '%G protein-coupled%'
group by gene_name, gene, chromosome
order by gene_name asc;

-- 5. 
-- What diseases have been positively linked to G protein-coupled receptors?
-- List the disease/phenotype and the number of publications (records) that report a positive association.
-- Use a sub-query, not a view

select phenotype, count(*) as num_records
from gad
where gene_name like '%G protein-coupled%'
group by phenotype
order by num_records desc;



-- 6. 
-- For genes on chromosome 3, what is the minimum, maximum DNA location
-- Exclude cases where the dna_start value is 0

select min(dna_start) as chromosome_3_min_loc, max(dna_end) as chromosome_3_max_loc
from gad
where chromosome = 3 and dna_start != 0;

-- 7. 
-- For each gene, what is the earliest and latest reported year
-- involving a positive association
-- Ignore records where the year isn't valid. (You have to determine what constitutes a valid year.)
-- Output the gene, min-year, max-year, and number of records
-- order by min-year, max-year, gene (3-level sorting)

select gene, min(year) as min_year, max(year) as max_year, count(*) as num_records
from gad
where year >= 1700
group by gene
order by min_year, max_year, gene;


-- 8. 
-- How many records are there for each gene?
-- Output the gene symbol and name and the count of the number of records
-- Order by the record count in descending order

select gene, gene_name, count(*) as num_records
from gad
group by gene, gene_name
order by num_records desc;

-- 9. 
-- Modify query 8 by considering only positive associations
-- and limit output to records having at least 100 associations

select gene, gene_name, count(*) as num_records
from gad
where association = 'Y'
group by gene, gene_name
having num_records >= 100
order by num_records desc;

-- 10. 
-- How many records are there for each population group?
-- Sort in descending order by count
-- Show only the top five records
-- Do NOT include cases where the population is blank

select population, count(*) as num_records
from gad
where population like '%_%'
group by population
order by num_records desc
limit 5;

-- 11.
-- What genes are linked to Asthma?  
-- Include any gene with a positive assoication to a phenotype containing the substring 'asthma'. 
-- Rank the genes by the number of publications that report the linkage (most to least).  
-- Output the gene (symbol), gene name, and number of publications. Include only genes that have been 
-- linked to asthma-related phenotypes in 2 or more publications.  
-- Create a VIEW called asthma_genes containing the output of these query.

drop view asthma_genes;
create view asthma_genes
as select gene, gene_name, count(*) as num_pubs
from gad
where phenotype like '%asthma%'
group by gene, gene_name
having num_pubs > 1
order by num_pubs desc;

-- 12.
-- With the help of your asthma_genes VIEW, find all distinct gene-phenotype 
-- associations involving asthma genes.  Sort your result by phenotype, then gene.
-- Exclude blank / empty phenotypes

select distinct 
	phenotype, 
	gene
from gad
where gene in (
	select gene
    from asthma_genes
    ) and phenotype like '%_%'
order by phenotype, gene;

-- 13. 
-- The phenotypes listed in the previous table are all phenotypes
-- linked to one or more asthma genes. For each of these phenotypes,
-- count the number of asthma genes linked to that phenotype.
-- Order from most to least. Show only the top 5 disease / phenotypes.   
-- Be sure to exclude both asthma-related phenotypes and empty phenotypes from this list!
-- Use a sub-query instead of a view.

-- How I understood the question:
	-- Found all genes linked to asthma phenotypes (henceforth, 'asthma linked genes'). [Q11]
	-- Found all other phenotypes linked to asthma linked genes (henceforth, 'asthma tangent phenotypes'). [Q12]
	-- Now count how many times each asthma tangent phenotype appears in the above list, indicating a stronger tangential relationship.

select phenotype, count(*) as link_count
from gad
where 
	gene in (
	select gene
    from asthma_genes
    ) 
    and phenotype like '%_%'
    and phenotype not like '%asthma%'
group by phenotype
order by link_count desc
limit 5;

-- 14.	
-- Consider asthma and the top disease found in the previous question.
-- What genes do they all have in common? (You may hard-code the names of the phenotypes in your query.)
-- For each gene, list how often it was reported to be positively linked to asthma and in a separate column
-- how often it was reported to be positively linked to your top disease.
-- Filter your result to show only those genes where links to both asthma and your top disease have been
-- reported multiple times (i.e., 3 or more times).

select 
	gene,
    sum(case when phenotype like '%asthma%' then 1 else 0 end) as asthma_links,
    sum(case when phenotype = 'diabetes, type 1' then 1 else 0 end) as diabetes1_links
from gad
group by gene
having 
	asthma_links > 2
	and diabetes1_links > 2
order by gene;

-- 15. 
-- Interpret your analysis:
-- a) Does existing biomedical research support a connection between asthma and the disease you identified above?
-- b) Why might a drug company be interested in instances of such "overlapping" phenotypes?

-- a) 	Yes: a study conducted in Taiwan found that individuals with type 1 diabetes were significantly more likely
-- 		to develop asthma than those who did not have type 1 diabetes (doi: 10.1097/MD.0000000000001466). The study 
-- 		did not, however, pinpoint exact reasons for this correlation.

--  b)	Phenotypes with a significant degree of overlap would be noteworthy to drug companies for several reasons:
-- 		for example, drug treatments would need to consider the potential side effects from both phenotypes, and
-- 		a correlation between two phenotypes could narrow down the possible causes of each condition, thus guiding 
-- 		further research.


-- 16. (10 points) OPEN RESEARCH QUESTION
-- What other interesting insight can you derive from this dataset using a SQL query? 
-- Perhaps something involving populations?  Publications? Disease class?  
-- Are some types of diseases the focus of more research?   This is an open-ended question! 
-- Impress me with your analytical creativity! But if you want full credit you have to:

-- a)	Ask a specific and interesting question
-- b)	Define a single SQL query that produces a result
-- c)	Present your answer (You may attach a separate .PDF file with your analysis including tables or figures.)


-- a) 	Question: Which disease classes are more prevalent for certain populations over others?
-- b) 	SQL query:

select
	disease_class,
    sum(case when population='Caucasian' then 1 else 0 end) as Caucasian,
    sum(case when population='Chinese' then 1 else 0 end) as Chinese,
    sum(case when population='Japanese' then 1 else 0 end) as Japanese,
    sum(case when population='African' then 1 else 0 end) as African,
    sum(case when population='Russian' then 1 else 0 end) as Russian,
    sum(case when 
		population='Russian' or 
		population='African' or 
		population='Japanese' or 
		population='Chinese' or 
		population='Caucasian' 
		then 1 else 0 end) as Total
from gad
where disease_class like '%_%'
group by disease_class
order by Total desc;

-- c) 	Analysis
-- 		While this analysis and query would be more effective using Unions, there are some things 
-- 		we can learn from the data presented here. Some examples: The Japanese appeare to have a much 
-- 		larger spike of immune diseases relative to their other disease as compared to other population.
-- 		Additionally, diseases effecting Africans and Russians appear to be severely neglected as compared 
-- 		to Caucasian, Chinese, and Japanese linked research. The Chinese appear to have a spike in 
-- 		neurological diseases as compared to other populations. Lastly, Russians have relatively more 
-- 		instances of cardiovascular diseases linked to their population. Caucasians had a relatively similar 
-- 		distribution of linked disease classes when compared to the totals, indicating fewer dramatic spikes.


