CREATE TABLE USER (
      user_id INTEGER PRIMARY KEY AUTOINCREMENT,
      username TEXT NOT NULL UNIQUE
  );

  CREATE TABLE URL (
      url_id INTEGER PRIMARY KEY AUTOINCREMENT,
      user_id INTEGER NOT NULL,
      url TEXT NOT NULL,
      shortened_url TEXT NOT NULL UNIQUE,
      FOREIGN KEY (user_id) REFERENCES USER(user_id)
  );


 -- lookup all URLs for a user
 CREATE INDEX idx_url_user_id ON URL(user_id);