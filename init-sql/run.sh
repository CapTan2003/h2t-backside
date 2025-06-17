#!/bin/bash

echo "⏳ Waiting for MySQL to be ready..."
while ! mysqladmin ping -h mysql -u root -proot --silent; do
    echo "⏳ MySQL not ready yet, waiting..."
    sleep 2
done
echo "✅ MySQL is ready!"

echo "⏳ Waiting for database 'study-english' to exist..."
while true; do
    mysql -h mysql -u root -proot -e "USE study-english;" &> /dev/null
    if [ $? -eq 0 ]; then
        echo "✅ Database 'study-english' exists!"
        break
    else
        echo "⏳ Database 'study-english' not ready yet..."
        sleep 3
    fi
done

echo "⏳ Waiting for backend to create tables (checking for 'airesponse' table)..."
while true; do
    mysql -h mysql -u root -proot -D study-english -e "DESCRIBE airesponse;" &> /dev/null
    if [ $? -eq 0 ]; then
        echo "✅ Table 'airesponse' exists! Backend has created tables."
        break
    else
        echo "⏳ Still waiting for backend to create tables..."
        sleep 5
    fi
done

echo "⏳ Starting data import..."
for sql_file in /init-sql-ro/*.sql; do
    if [ -f "$sql_file" ]; then
        echo "📥 Importing $(basename "$sql_file")..."
        mysql -h mysql -u root -proot study-english < "$sql_file"
        if [ $? -eq 0 ]; then
            echo "✅ Successfully imported $(basename "$sql_file")"
        else
            echo "❌ Failed to import $(basename "$sql_file")"
        fi
    fi
done

echo "✅ All data import completed!"