CREATE TABLE clan (
    clan_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(36) NOT NULL,
    gold INTEGER
);

CREATE TABLE task(
     task_id BIGINT AUTO_INCREMENT PRIMARY KEY,
     description VARCHAR(100) NOT NULL,
     gold_given INT
);

CREATE TABLE user(
     user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
     name VARCHAR(16) NOT NULL,
     surname VARCHAR(16) NOT NULL,
     clan_id BIGINT
);

CREATE TABLE transaction(
     transaction_id BIGINT AUTO_INCREMENT PRIMARY KEY,
     clan_id BIGINT NOT NULL,
     delta INT NOT NULL,
     source VARCHAR(6),
     is_successful BOOLEAN
);

CREATE TABLE user_transaction(
    transaction_id BIGINT PRIMARY KEY,
    user_id BIGINT PRIMARY KEY
);

CREATE TABLE task_transaction(
     transaction_id BIGINT PRIMARY KEY,
     task_id BIGINT PRIMARY KEY
);

ALTER TABLE user ADD FOREIGN KEY (clan_id) REFERENCES clan(clan_id);
ALTER TABLE transaction ADD FOREIGN KEY (clan_id) REFERENCES clan(clan_id);
ALTER TABLE user_transaction ADD FOREIGN KEY (transaction_id) REFERENCES transaction(transaction_id);
ALTER TABLE user_transaction ADD FOREIGN KEY (user_id) REFERENCES user(user_id);
ALTER TABLE task_transaction ADD FOREIGN KEY (transaction_id) REFERENCES transaction(transaction_id);
ALTER TABLE task_transaction ADD FOREIGN KEY (task_id) REFERENCES task(task_id);