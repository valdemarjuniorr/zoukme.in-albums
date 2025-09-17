-- create a new field and make it unique if true
ALTER TABLE events ADD COLUMN feature_event BOOLEAN DEFAULT FALSE;

CREATE UNIQUE INDEX idx_unique_feature_event ON events (feature_event) WHERE feature_event = TRUE;
