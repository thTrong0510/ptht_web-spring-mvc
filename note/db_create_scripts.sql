CREATE DATABASE IF NOT EXISTS ticket_booking_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE ticket_booking_db;

-- =========================
-- ROLES
-- =========================
CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    is_active BOOLEAN DEFAULT TRUE
) ENGINE=InnoDB;

-- =========================
-- USERS
-- =========================
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    avatar_url TEXT NOT NULL,
    role_id BIGINT NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP 
        ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_users_role
        FOREIGN KEY (role_id) REFERENCES roles(id)
        ON DELETE RESTRICT
) ENGINE=InnoDB;

-- =========================
-- PERMISSIONS
-- =========================
CREATE TABLE permissions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    api_path VARCHAR(255),
    api_method VARCHAR(10),
    module VARCHAR(100)
) ENGINE=InnoDB;

CREATE TABLE role_permission (
    role_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    PRIMARY KEY (role_id, permission_id),

    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE,
    FOREIGN KEY (permission_id) REFERENCES permissions(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- =========================
-- ORGANIZER VERIFICATIONS
-- =========================
CREATE TABLE organizer_verifications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    status VARCHAR(50) NOT NULL,
    approved_by BIGINT NULL,
    approved_at TIMESTAMP NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (approved_by) REFERENCES users(id) ON DELETE SET NULL
) ENGINE=InnoDB;

-- =========================
-- CATEGORIES
-- =========================
CREATE TABLE categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT
) ENGINE=InnoDB;

-- =========================
-- EVENT STATUS
-- =========================
CREATE TABLE event_status (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description TEXT
) ENGINE=InnoDB;

-- =========================
-- EVENTS
-- =========================
CREATE TABLE events (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    organizer_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    status_id BIGINT NOT NULL,

    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    image_url TEXT,
    video_url TEXT,

    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    location VARCHAR(255) NOT NULL,

    total_tickets INT NOT NULL,
    ticket_price DECIMAL(15,2) NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP 
        ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (organizer_id) REFERENCES users(id) ON DELETE RESTRICT,
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE RESTRICT,
    FOREIGN KEY (status_id) REFERENCES event_status(id) ON DELETE RESTRICT
) ENGINE=InnoDB;

-- =========================
-- ORDERS (1 ORDER = 1 EVENT)
-- =========================
CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    event_id BIGINT NOT NULL,

    quantity INT NOT NULL,
    unit_price DECIMAL(15,2) NOT NULL,
    total_amount DECIMAL(15,2) NOT NULL,

    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE RESTRICT,
    FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE RESTRICT
) ENGINE=InnoDB;

-- =========================
-- TICKETS
-- =========================
CREATE TABLE tickets (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    event_id BIGINT NOT NULL,
    attendee_id BIGINT NOT NULL,

    ticket_code VARCHAR(100) NOT NULL UNIQUE,
    check_in_time TIMESTAMP NULL,

    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE RESTRICT,
    FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE RESTRICT,
    FOREIGN KEY (attendee_id) REFERENCES users(id) ON DELETE RESTRICT
) ENGINE=InnoDB;

-- =========================
-- PAYMENTS
-- =========================
CREATE TABLE payments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    payment_method VARCHAR(50) NOT NULL,
    amount DECIMAL(15,2) NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP 
        ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE RESTRICT
) ENGINE=InnoDB;

-- =========================
-- PAYMENT TRANSACTIONS
-- =========================
CREATE TABLE payment_transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    payment_id BIGINT NOT NULL,
    provider VARCHAR(50),
    provider_transaction_id VARCHAR(255) UNIQUE,
    payer_email VARCHAR(255),
    payer_id VARCHAR(255),
    provider_status VARCHAR(100),
    raw_response JSON,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (payment_id) REFERENCES payments(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- =========================
-- EVENT STATISTICS
-- =========================
CREATE TABLE event_statistics (
    event_id BIGINT PRIMARY KEY,
    total_tickets_sold INT DEFAULT 0,
    total_revenue DECIMAL(15,2) DEFAULT 0,
    total_views INT DEFAULT 0,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP 
        ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- =========================
-- SYSTEM DAILY STATISTICS
-- =========================
CREATE TABLE system_statistics_daily (
    stat_date DATE PRIMARY KEY,
    total_orders INT DEFAULT 0,
    total_revenue DECIMAL(15,2) DEFAULT 0,
    total_events INT DEFAULT 0
) ENGINE=InnoDB;

-- =========================
-- IMPORTANT INDEXES
-- =========================
CREATE INDEX idx_orders_status ON orders(status);
CREATE INDEX idx_orders_created_at ON orders(created_at);
CREATE INDEX idx_orders_event ON orders(event_id);
CREATE INDEX idx_events_status ON events(status_id);
CREATE INDEX idx_events_category ON events(category_id);