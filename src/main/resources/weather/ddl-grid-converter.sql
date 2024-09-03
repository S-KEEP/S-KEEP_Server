create table location_grid (
       id int auto_increment primary key,
       division varchar(50),
       administrative_code varchar(20),
       level1 varchar(50),
       level2 varchar(50),
       level3 varchar(50),
       grid_x int,
       grid_y int,
       longitude_degree int,
       longitude_minute int,
       longitude_second decimal(10, 2),
       latitude_degree int,
       latitude_minute int,
       latitude_second decimal(10, 2),
       longitude_second_per_100 decimal(15, 12),
       latitude_second_per_100 decimal(15, 12)
);

select * from location_grid;
