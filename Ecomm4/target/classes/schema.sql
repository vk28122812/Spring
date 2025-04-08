CREATE TABLE Ebook (
    id INTEGER NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    publisher VARCHAR(255) NOT NULL,
    price DOUBLE NOT NULL,
    publish_date DATE NOT NULL,
    discount DOUBLE DEFAULT 0,
    PRIMARY KEY (id)
);