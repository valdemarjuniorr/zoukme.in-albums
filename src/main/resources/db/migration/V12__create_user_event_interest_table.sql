CREATE TABLE user_event_interest (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    event_id BIGINT NOT NULL REFERENCES events(id) ON DELETE CASCADE,
    interest VARCHAR(20) NOT NULL CHECK (interest IN ('INTERESTED', 'GOING')),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(user_id, event_id)
);

CREATE INDEX idx_user_event_interest_user_id ON user_event_interest(user_id);
CREATE INDEX idx_user_event_interest_event_id ON user_event_interest(event_id);
