INSERT INTO conferences(conference_id, conference_nameen_US, conference_nameuk_UA, conference_datetime, conference_locationen_US, conference_locationuk_UA) VALUES
(1,'Comic Con 2020','Комі Кон 2020','2020-11-20 18:00:00','Kyiv, Ave Nezalezhnosti, 70','Київ, проспект Незалежності 70'),
(2,'SQL 2020','SQL 2020','2020-10-20 11:30:00','Kharkiv, Sumska street 20','Харків, вулиця Сумська 20'),
(5,'Java Event 2020','Джава захід 2020','2020-12-12 15:00:00','Lviv, Grushevskoho street 12','Львів, вулиця Грушевського 12'),
(8,'Kharkiv Project Management Day 2020','Харків Проджект Менеджемент День 2020','2020-12-09 20:10:00','Kharkiv, Sumska street 25.','Харків, вулиця Сумська 25');

INSERT INTO users(user_id, user_login, user_password, user_first_nameen_US, user_first_nameuk_UA, user_last_nameen_US, user_last_nameuk_UA, user_email, user_role) VALUES
(1,'oleg182','$2a$10$nNLwkaYMWjgr/tDkyPLmQuJazQD1GmzNA8v0Exj8e8omXMKVkAAVC','Oleg','Олег','Chornyi','Чорний','olegchornyi182@gmail.com','MODERATOR'),
(2,'speaker11','$2a$10$nNLwkaYMWjgr/tDkyPLmQuJazQD1GmzNA8v0Exj8e8omXMKVkAAVC ','Ivan','Іван','Ivanov','Іванов','ivanov@gmail.com','SPEAKER'),
(3,'user11','$2a$10$nNLwkaYMWjgr/tDkyPLmQuJazQD1GmzNA8v0Exj8e8omXMKVkAAVC','Anton','Антон','Antonov','Антонов','antonov@gmail.com','USER'),
(4,'speaker22','$2a$10$nNLwkaYMWjgr/tDkyPLmQuJazQD1GmzNA8v0Exj8e8omXMKVkAAVC','Viktoria','Вікторія','Viktorivna','Вікторовна','viktorovna@gmail.com','SPEAKER');

INSERT INTO users_conferences(user_id, conference_id) VALUES
(2,1),(2,5),(3,1),(3,8),
(4,5),(2,2),(4,1);

INSERT INTO conversations(conversation_id, conversation_topicen_US, conversation_topicuk_UA, conversation_datetime, speaker_id) VALUES
(1,'Marvel','Марвел','2020-11-20 19:00:00',2),
(2,'DC','ДС','2020-11-20 19:30:00', 4),
(3,'Spring','Спрінг','2020-12-12 16:00:00', 2),
(4,'Scrum','Скрам','2020-12-09 21:00:00', 4),
(5,'MongoDB','Монго БД','2020-10-20 12:15:00', 2);

INSERT INTO conversations_conferences(conversation_id, conference_id) VALUES
(1,1),(2,1),(3,5),(4,8),(5,2);

