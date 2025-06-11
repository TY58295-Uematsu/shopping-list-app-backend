-- V1__create_initial_tables.sql

-- users テーブル
CREATE TABLE users
(
    id          BIGSERIAL PRIMARY KEY,
    user_name   TEXT        NOT NULL,
    mail        TEXT UNIQUE NOT NULL,
    root_shop INT
);

-- shops テーブル
CREATE TABLE shops
(
    id        BIGSERIAL PRIMARY KEY,
    shop_name TEXT NOT NULL
);

-- shopping_items テーブル
CREATE TABLE shopping_items
(
    id      BIGSERIAL PRIMARY KEY,
    user_id BIGINT  NOT NULL,
    shop_id BIGINT  NOT NULL,
    item    TEXT    NOT NULL,
    buy     BOOLEAN NOT NULL DEFAULT FALSE, -- デフォルト値
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_shop FOREIGN KEY (shop_id) REFERENCES shops (id)
);

-- 初期データの挿入（オプション）
INSERT INTO users (user_name,mail, root_shop ) VALUES ('wentz', 'wentz@mail.com', 1);
INSERT INTO shops (shop_name) VALUES ('スーパー');
INSERT INTO shops (shop_name) VALUES ('ドラッグストア');
INSERT INTO shops (shop_name) VALUES ('100均');
INSERT INTO shops (shop_name) VALUES ('雑貨屋');