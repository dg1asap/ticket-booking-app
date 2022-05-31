#!/bin/bash

# creating a menu with the following options
echo "COMMAND LIST"
echo "1. GET /movie-shows"
echo "2. GET /movie-shows/period/"
echo "3. GET /movie-shows/{id}/room"
echo "4. GET /movie-shows/{id}/available-seats"
echo "5. GET /customer/"
echo "6. GET /customer/reservation/movie-show-id/{id}/"
echo "7. GET /customer/reservation/"
echo "8. PUT /customer/reservation/movie-show/{movie-show-id}/seat/{seat-id}"
echo "9. PUT /customer/relief/"
echo "10. POST /customer"
echo "11. Exit from menu "
echo -n "Enter your menu choice [1-11]: "

# Running a forever loop using while statement
# This loop will run untill select the exit option.
# User will be asked to select option again and again
while :
do

# reading choice
read choice

# case statement is used to compare one value with the multiple cases.
case $choice in
  # Pattern 1
  1)  curl http://localhost:8080/movie-shows | json_pp;
      echo "You have selected the option 1"
      echo " ";;
  # Pattern 2
  2)  curl http://localhost:8080/movie-shows/period/\?from\=16:00-5/5/2022\&to\=23:00-22/12/2022 | json_pp;
      echo "You have selected the option 2"
      echo "This option";;
  # Pattern 3
  3)  curl http://localhost:8080/movie-shows/1010/room | json_pp
      echo "You have selected the option 3"
      echo " ";;    
  # Pattern 4
  4)  curl http://localhost:8080/movie-shows/1010/available-seats | json_pp
      echo "You have selected the option 4"
      echo "This url return ";;    
  # Pattern 5
  5)  curl http://localhost:8080/customer/\?name\=Johnny\&surname\=Depp | json_pp
      echo "You have selected the option 5"
      echo " ";;    
  # Pattern 6
  6)  curl http://localhost:8080/customer/reservation/movie-show/1010/\?name-of-customer\=Johnny\&surname-of-customer\=Depp | json_pp
      echo "You have selected the option 6"
      echo " ";;    
  # Pattern 7
  7)  curl http://localhost:8080/customer/reservation/\?name-of-customer\=Johnny\&surname-of-customer\=Depp | json_pp
      echo "You have selected the option 7"
      echo " ";;    
  # Pattern 8
  8)    curl --location --request PUT 'http://localhost:8080/customer/reservation/movie-show/1010/seat/9800503/' \
	--header 'Content-Type: application/json' \
	--data-raw '{
	    "name": "Johnny",
	    "surname": "Depp"
	}' | json_pp

	curl --location --request PUT 'http://localhost:8080/customer/reservation/movie-show/1010/seat/9800504/' \
	--header 'Content-Type: application/json' \
	--data-raw '{
	    "name": "Johnny",
	    "surname": "Depp"
	}' | json_pp

	curl --location --request PUT 'http://localhost:8080/customer/reservation/movie-show/1010/seat/9800505/' \
	--header 'Content-Type: application/json' \
	--data-raw '{
	    "name": "Johnny",
	    "surname": "Depp"
	}' | json_pp

	curl --location --request PUT 'http://localhost:8080/customer/reservation/movie-show/1010/seat/9800506/' \
	--header 'Content-Type: application/json' \
	--data-raw '{
	    "name": "Johnny",
	    "surname": "Depp"
	}' | json_pp

	curl --location --request PUT 'http://localhost:8080/customer/reservation/movie-show/1010/seat/9800207/' \
	--header 'Content-Type: application/json' \
	--data-raw '{
	    "name": "Johnny",
	    "surname": "Depp"
	}' | json_pp
      echo "You have selected the option 8"
      echo " ";;    
  # Pattern 9
  9)  curl --location --request PUT 'http://localhost:8080/customer/Johnny/Depp/relief/student'
      echo "You have selected the option 9"
      echo " ";;    
  # Pattern 10
  10)   curl --location --request POST 'http://localhost:8080/customer' \
	--header 'Content-Type: application/json' \
	--data-raw '{
	    "name": "Mike",
	    "surname": "Tyson"
	}' | json_pp
      echo "You have selected the option 10"
      echo " ";;    
  # Pattern 11
  11)  echo "Quitting ..."
      exit;;
  # Default Pattern
  *) echo "invalid option";;
  
esac
  echo -n "Enter your menu choice [1-11]: "
done
