docker run --name lotification-mysql -d --rm -p 3306:3306 \
-v lotification:/var/lib/mysql \
-e MYSQL_ROOT_PASSWORD=1234 \
-e MYSQL_DATABASE=lotification \
-e MYSQL_USER=dud708 \
-e MYSQL_PASSWORD=1234 \
mysql:8