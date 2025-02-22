BEGIN TRANSACTION;
DROP TABLE IF EXISTS "books";
CREATE TABLE IF NOT EXISTS "books" (
	"id"	INTEGER NOT NULL UNIQUE,
	"title"	TEXT NOT NULL,
	"author"	TEXT,
	"edition"	TEXT,
	"year"	TEXT,
	"publisher"	TEXT,
	"summary"	TEXT,
	"cover"	TEXT,
	"price"	TEXT,
	"status"	TEXT,
	PRIMARY KEY("id" AUTOINCREMENT)
);
DROP TABLE IF EXISTS "librarians";
CREATE TABLE IF NOT EXISTS "librarians" (
	"id"	INTEGER NOT NULL UNIQUE,
	"username"	TEXT NOT NULL UNIQUE,
	"password"	TEXT NOT NULL,
	"name"	TEXT NOT NULL,
	"address"	TEXT,
	"photo"	TEXT,
	"status"	TEXT,
	PRIMARY KEY("id" AUTOINCREMENT)
);
DROP TABLE IF EXISTS "readers";
CREATE TABLE IF NOT EXISTS "readers" (
	"id"	INTEGER NOT NULL UNIQUE,
	"name"	TEXT NOT NULL,
	"address"	TEXT,
	"photo"	TEXT,
	"status"	TEXT,
	PRIMARY KEY("id" AUTOINCREMENT)
);
DROP TABLE IF EXISTS "borrows";
CREATE TABLE IF NOT EXISTS "borrows" (
	"id"	INTEGER NOT NULL UNIQUE,
	"book_id"	INTEGER NOT NULL,
	"reader_id"	INTEGER NOT NULL,
	"date_borrowed"	TEXT NOT NULL,
	"date_to_return"	TEXT NOT NULL,
	"date_returned"	TEXT,
	"librarian_id"	INTEGER,
	"active"	INTEGER NOT NULL DEFAULT 1,
	FOREIGN KEY("reader_id") REFERENCES "readers"("id"),
	FOREIGN KEY("librarian_id") REFERENCES "librarians"("id"),
	FOREIGN KEY("book_id") REFERENCES "books"("id"),
	PRIMARY KEY("id" AUTOINCREMENT)
);
INSERT INTO "books" VALUES (1,'Java 14 Development of Applications with JavaFX','Poul Klausen','1','2018','bookboon','JavaFX is intended as an alternative to Swing, and it is especially for better graphics and other media used in the user interface. With JavaFX, it is easier to develop applications with a modern user interface and with all the possibilities that users expect and get used to from web applications. However, it is important to emphasize that you can still use Swing
components, so that all known facilities from Swing are still available.','file:./src/jlib/storage/covers/Java%2014%20Development%20of%20Applications%20with%20JavaFX%20%5BKlausen,%202018%5D.jpg','23 $','available');
INSERT INTO "books" VALUES (2,'Mastering JavaFX 10','Sergey Grinev','1','2018','Packt','JavaFX is a set of libraries added to Java in order to provide capabilities to build a modern UI. It was added to Java a few releases ago, as old libraries Swing and AWT proved to be outdated and too burdened with backward compatibility issues.
JavaFX was designed and developed from scratch to provide Java developers with the capabilities to build modern, rich UI applications with a large set of shapes, controls, and charts. It was designed with performance in mind, is capable of using graphics cards, and is based on the new graphical engine.
In this book, we will study many aspects of JavaFX and go through a large set of examples based on these topics.','file:./src/jlib/storage/covers/Mastering%20JavaFX%2010%20%5BGrinev,%202018%5D.jpg','20 €','available');
INSERT INTO "books" VALUES (3,'Java in Two Semesters Featuring JavaFX','Quentin Charatan, Aaron Kans','4','2019','Springer','One key feature of this new edition is that all graphical user interface developments are based on JavaFX, rather than the Swing Technology used in previous editions. JavaFX allows for the creation of sophisticated modern graphical interfaces that can run on a variety of devices and is now Oracle''s preferred technology for building such interfaces, having decided that Swing will no longer be developed. JavaFX therefore plays a very significant role throughout the new text, and three new chapters are devoted to it.','file:./src/jlib/storage/covers/Java%20in%20Two%20Semesters%20Featuring%20JavaFX%20%5BCharatan,%204,%202019%5D.jpg','10 $','borrowed');
INSERT INTO "books" VALUES (4,'Head First C#','Jennifer Greene, Andrew Stellman','4','2020','O''Reilly','C# is a simple, modern language that lets you do incredible things.
And when you learn C#, you''re learning more than just a language: C# unlocks the whole world of .NET, an incredibly powerful open source platform for building all sorts of programs: desktop, web, and mobile apps; cloud computing; machine learning and artificial intelligence; 2D and 3D gaming; and much, much more.','file:./src/jlib/storage/covers/Head%20First%20C%23%20%5BStellman,%204,%202020%5D.jpg','20 €','available');
INSERT INTO "books" VALUES (5,'Becoming a Better Programmer','Pete Goodliffe','2','2015','O''Reilly','If you''re passionate about programming and want to get better at it, you''ve come to the right source. Code Craft author Pete Goodliffe presents a collection of useful techniques and approaches to the art and craft of programming that will help boost your career and your well-being.
Goodliffe presents sound advice that he''s learned in 15 years of professional programming. The book''s standalone chapters span the range of a software developer''s life—dealing with code, learning the trade, and improving performance—with no language or industry bias. Whether you''re a seasoned developer, a neophyte professional, or a hobbyist, you''ll find valuable tips in five independent categories.','file:./src/jlib/storage/covers/Becoming%20a%20Better%20Programmer%20%5BGoodliffe,%202,%202015%5D.jpg','50 lei','available');
INSERT INTO "books" VALUES (6,'The Art of Computer Programming','Donald Knuth','3','1997','Addison-Wesley','This set of books is intended for people who will be more than just casually interested in computers, yet it is by no means only for the computer specialist.
Indeed, one of my main goals has been to make these programming techniques more accessible to the many people working in other fields who can make fruitful use of computers, yet who cannot afford the time to locate all of the necessary information that is buried in technical journals.','file:./src/jlib/storage/covers/The%20Art%20of%20Computer%20Programming%20%5BKnuth,%20I-IVa%5D.jpg','55 $','available');
INSERT INTO "books" VALUES (7,'Modern C++ Programming Cookbook','Marius Băncilă','2','2020','Packt','This book addresses many of the new features included in C++11, C++14, C++17, and the forthcoming C++20. This book is organized in recipes, each covering one particular language or library feature, or a common problem that developers face and its typical solution using modern C++. Through more than 130 recipes, you will learn to master both core language features and the standard libraries, including those for strings, containers, algorithms, iterators, streams, regular expressions, threads, filesystem, atomic operations, utilities, and ranges.','file:./src/jlib/storage/covers/Modern%20C++%20Programming%20Cookbook%20%5BBancila,%202,%202020%5D.jpg','27 €','available');
INSERT INTO "books" VALUES (8,'The C++ Programming Language','Bjarne Stroustrup','4','2013','Addison-Wesley','This book assumes that its readers are programmers. If you ask, ‘‘What’s a for-loop?’’ or ‘‘What’s a compiler?’’ then this book is not (yet) for you; instead, I recommend my Programming: Principles and Practice Using C++ to get started with programming and C++. Furthermore, I assume that readers have some maturity as software developers. If you ask ‘‘Why bother testing?’’ or say, ‘‘All languages are basically the same; just show me the syntax’’ or are confident that there is a single language that is ideal for every task, this is not the book for you.','file:./src/jlib/storage/covers/The%20C++%20Programming%20Language%20%5BStroustrup,%204,%202013%5D.jpg','20 $','available');
INSERT INTO "books" VALUES (9,'Design Patterns Explained Simply','Alexander Shvets','1','2013','Sourcemaking.com','With so many design patterns in the world, you easily forget them when you need them. This book is my goto source for looking up patterns as I need them. It gives you an easy overview of the different patterns. eg when you know you are in need of a creational pattern, but cant figure out which one to use, this book makes it really easy to refresh your memory and choose the right pattern.','file:./src/jlib/storage/covers/Design%20Patterns%20Explained%20Simply%20%5BShvets,%202013%5D.jpg','20 lei','available');
INSERT INTO "books" VALUES (10,'Learning C# programming with Unity 3D','Alex Okita','2','2020','CRC Press','This book was written as an answer for anyone to pick up a modern programming language and be productive. You will be able to start a simple game in Unity 3D from scratch. By the end of this book, you will have the basic skills to eventually become a capable unity game programmer, or at least know what is involved with how to read and write some code.
You should have general computer skills before you get started. Come prepared; you’ll need a modern Windows or OSX computer capable of running modern software with an internet connection. After this book, you should be armed with the knowledge required to feel confident in learning more.
Each chapter has example code organized by chapter and section. We’ll try to make a fun project starting with the basic functions of a typical game and we’ll see how the basic game can be expanded upon and we’ll learn a bit about what’s involved with a larger project.','file:./src/jlib/storage/covers/Learning%20C%23%20programming%20with%20Unity%203D%20%5BOkita,%202,%202020%5D.jpg','20 $','available');
INSERT INTO "librarians" VALUES (1,'admin','pass','Alin Clincea','Craiova','file:./src/jlib/storage/librarians/alin.clincea.jpg','administrator');
INSERT INTO "librarians" VALUES (2,'alex','123','Alexandru Georgescu','București','file:./src/jlib/storage/librarians/alexandru.georgescu.jpg','librarian');
INSERT INTO "librarians" VALUES (3,'ionela','qwe','Ionela Marin','Slatina, Olt','file:./src/jlib/storage/librarians/ionela.marin.jpg','librarian');
INSERT INTO "readers" VALUES (1,'Alin Clincea','Craiova, Romania','file:./src/jlib/storage/librarians/alin.clincea.jpg','active');
INSERT INTO "readers" VALUES (2,'James Gosling','Calgary, Canada','file:./src/jlib/storage/readers/james.gosling.jpg','active');
INSERT INTO "readers" VALUES (3,'Miruna Dumitriu','Filiași, Dolj','file:./src/jlib/storage/readers/miruna.dumitriu.jpg','inactive');
INSERT INTO "readers" VALUES (4,'Elena Popa','Cârcea, Dolj','file:./src/jlib/storage/readers/elena.popa.jpg','active');
INSERT INTO "readers" VALUES (5,'Cătălin Ionescu','Craiova, Dolj','file:./src/jlib/storage/readers/catalin.ionescu.jpg','banned');
INSERT INTO "readers" VALUES (6,'Angela Ștefan','Târgu Jiu, Gorj','file:./src/jlib/storage/readers/angela.stefan.jpg','active');
INSERT INTO "borrows" VALUES (1,7,1,'2021-01-08','2021-01-23','2021-01-08',1,0);
INSERT INTO "borrows" VALUES (2,8,2,'2021-01-08','2021-01-23','2021-01-08',1,0);
INSERT INTO "borrows" VALUES (3,3,5,'2021-01-08','2021-01-23',NULL,1,1);
COMMIT;
