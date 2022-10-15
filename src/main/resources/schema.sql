DROP TABLE IF EXISTS nace_orders;
  
CREATE TABLE nace_orders(
  id INT PRIMARY KEY,
  level INT NOT NULL,
  code VARCHAR2 NOT NULL,
  parent VARCHAR2,
  description VARCHAR2,
  item_inclusions CLOB,
  item_inclusions_extra CLOB,
  rulings VARCHAR2,
  item_exclusions CLOB,
  isic_rev_ref VARCHAR2 NOT NULL
);