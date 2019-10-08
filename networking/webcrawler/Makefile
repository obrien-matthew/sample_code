all:
	mvn package -Dmaven.test.skip=true
	echo 'java -jar ./target/crawler-1.0.jar $$1 $$2' > webcrawler
	chmod +x webcrawler

clean:
	mvn clean
	rm webcrawler
