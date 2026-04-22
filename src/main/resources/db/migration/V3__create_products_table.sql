CREATE TABLE IF NOT EXISTS products (
                          id            BIGSERIAL    PRIMARY KEY,
                          name          VARCHAR(255) NOT NULL,
                          stock         INTEGER      NOT NULL DEFAULT 0,
                          min_threshold INTEGER      NOT NULL DEFAULT 1,

                          CONSTRAINT chk_stock_non_negative  CHECK (stock         >= 0),
                          CONSTRAINT chk_threshold_positive  CHECK (min_threshold >= 1)
);