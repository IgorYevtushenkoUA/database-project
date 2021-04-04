create extension if not exists pgcrypto;

INSERT INTO "user_entity" (username, password)
VALUES ('admin', crypt('admin', gen_salt('bf', 8)));