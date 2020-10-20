SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT;
SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS;
SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO';


SET character_set_client = utf8;
CREATE TABLE conferences(
    conference_id INT NOT NULL AUTO_INCREMENT,
    conference_nameen_US VARCHAR(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    conference_nameuk_UA VARCHAR(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    conference_datetime DATETIME NOT NULL,
    conference_locationen_US VARCHAR(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    conference_locationuk_UA VARCHAR(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    PRIMARY KEY (conference_id),
    UNIQUE KEY conference_id_Uniq(conference_id)
);

SET character_set_client = utf8;
CREATE TABLE users(
    user_id INT NOT NULL AUTO_INCREMENT,
    user_login VARCHAR(50) COLLATE utf8mb4_unicode_ci NOT NULL,
    user_password VARCHAR(100) COLLATE utf8mb4_unicode_ci NOT NULL,
    user_first_nameen_US VARCHAR(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    user_first_nameuk_UA VARCHAR(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    user_last_nameen_US VARCHAR(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    user_last_nameuk_UA VARCHAR(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    user_email VARCHAR(100) COLLATE utf8mb4_unicode_ci NOT NULL,
    user_role VARCHAR(30) COLLATE utf8mb4_unicode_ci NOT NULL,
    PRIMARY KEY (user_id),
    UNIQUE KEY user_id_Unique(user_id),
    UNIQUE KEY user_login_Unique(user_login),
    UNIQUE KEY user_email_Unique(user_email)
);

SET character_set_client = utf8;
CREATE TABLE conversations(
    conversation_id INT NOT NULL AUTO_INCREMENT,
    conversation_topicen_US VARCHAR(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    conversation_topicuk_UA VARCHAR(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    conversation_datetime DATETIME NOT NULL,
    speaker_id int NOT NULL,
    PRIMARY KEY (conversation_id),
    UNIQUE KEY conversation_id_Unique(conversation_id),
    KEY conversations1(speaker_id),
    CONSTRAINT conversation2
        FOREIGN KEY (speaker_id)
            REFERENCES users(user_id)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

SET character_set_client = utf8;
CREATE TABLE conversations_conferences(
    conversation_id INT NOT NULL,
    conference_id INT NOT NULL,
    KEY con_cof1(conversation_id),
    KEY con_cof2(conference_id),
    CONSTRAINT con_cof_1
        FOREIGN KEY (conversation_id)
            REFERENCES conversations(conversation_id)
            ON DELETE CASCADE
            ON UPDATE NO ACTION,
    CONSTRAINT con_cof_2
        FOREIGN KEY (conference_id)
            REFERENCES conferences(conference_id)
            ON DELETE CASCADE
            ON UPDATE NO ACTION
);

SET character_set_client = utf8;
CREATE TABLE users_conferences(
    user_id INT NOT NULL,
    conference_id INT NOT NULL,
    KEY user_cof1(user_id),
    KEY user_cof2(conference_id),
    CONSTRAINT user_cof_1
        FOREIGN KEY (user_id)
            REFERENCES users(user_id)
            ON DELETE CASCADE
            ON UPDATE NO ACTION,
    CONSTRAINT user_cof_2
        FOREIGN KEY (conference_id)
            REFERENCES conferences(conference_id)
            ON DELETE CASCADE
            ON UPDATE NO ACTION
);