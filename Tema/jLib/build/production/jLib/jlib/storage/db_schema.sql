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
	PRIMARY KEY("id" AUTOINCREMENT),
	FOREIGN KEY("reader_id") REFERENCES "readers"("id"),
	FOREIGN KEY("book_id") REFERENCES "books"("id"),
	FOREIGN KEY("librarian_id") REFERENCES "librarians"("id")
);
COMMIT;
