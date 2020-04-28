create table events
       (
  
        id INT NOT NULL AUTO_INCREMENT,
  
        event_id VARCHAR(100),
 
        event_name VARCHAR(100),

        event_date Date,
   
        business_unit VARCHAR(100),
  
        venue VARCHAR(100),
  
        total_volunteers INT(15),
  
        total_participants INT(15),
  
        lives_impacted INT(15),
 
        volunteer_hours INT(15),
   
        travel_hours INT(15),
  
        status VARCHAR(100),
  
        base_location VARCHAR(100),
   
        beneficiary_name VARCHAR(100),
   
        council_name VARCHAR(100),
   
        event_description VARCHAR(100),
  
        PRIMARY KEY ( id )
       
);