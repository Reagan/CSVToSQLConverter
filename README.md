CSVToSQLConverter
=================
CSVToSQLConverter is a command line tool that converts a csv file or a set of csv files to an SQL dump output. Currently, the tool only supports MySQL and the SQL 2009 standard. 

*Usage*

1. Running the CSVToSQLConverter

To ran the CSVToSQLConverter, a user will need to create a config file and specify a path to the set of csv files. The path to the csv files may be an absolute path to a csv files or a folder with csv files. The path may also be a relative path from the current classpath.

A csv file must only contain information that is to be populated into one table. 

The config file specifies the table schema for each of csv files whose contents have to be converted into an SQL Dump file. 

CSVToSQLConverter can be ran from command line using the jar command or added to $PATH so that the command CSVToSQLConverter is ran as a command from terminal. 

Running the CSVToSQLConverter as a jar file
To run the CSVToSQLConverter as a jar file, use the command

>> java -jar CSVToSQLConverter -p /path/to/csv/files -c /path/to/config/file

To add the CSVTOSQLConverter to $PATH to /etc/environment file. The CSVToSQLConverter can then be ran from command line using the command

>> CSVToSQLConverter -p /path/to/csv/files/ -c /path/to/config/file

The script terminates if the path to csv files or the config file to each of the csv files is not specified. 

2. Creating the config file 

Each of the csv files that requires to be converted to SQL requires to be declared in the config file. An example config file may be structured as follows:

[csvfile1]
id=int, auto_increment 
firstname=varchar(255)
secondname=varchar(255)
primarykey=autoincrement 
foreignkey=id REFERENCES cars.owner_id

This config file may be mapped to a csv file that must be named csvfile.csv with the following structure:

id,firstname,secondname
1,"Antony","Ojwang"
2,"Reagan","Mbitiru"

All csv files must have the first row specifying the table columns for the table to be generated.

Additional examples can be found in the documentation folder of this repository



