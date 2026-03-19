-- Add indexes for frequently queried columns

-- EventRepository queries
CREATE INDEX IF NOT EXISTS idx_events_date ON events(date);
CREATE INDEX IF NOT EXISTS idx_events_event_url ON events(event_url);
CREATE INDEX IF NOT EXISTS idx_events_feature_event_date ON events(feature_event, date) WHERE feature_event = false;

-- AlbumRepository queries
CREATE INDEX IF NOT EXISTS idx_albums_event_date_desc ON albums(event_date DESC);
CREATE INDEX IF NOT EXISTS idx_albums_event_id ON albums(event_id);
CREATE INDEX IF NOT EXISTS idx_albums_url ON albums(url);
CREATE INDEX IF NOT EXISTS idx_albums_city ON albums(city);

-- PhotoRepository queries
CREATE INDEX IF NOT EXISTS idx_photos_event_id ON photos(event_id);

-- EventPhotosRepository queries
CREATE INDEX IF NOT EXISTS idx_event_photos_sub_event_id ON event_photos(sub_event_id);

-- SubEventRepository queries
CREATE INDEX IF NOT EXISTS idx_sub_events_event_id ON sub_events(event_id);
CREATE INDEX IF NOT EXISTS idx_sub_events_name_event_id ON sub_events(name, event_id);

-- SocialMediaRepository queries
CREATE INDEX IF NOT EXISTS idx_social_media_event_id ON social_media(event_id);

-- PackageRepository queries
CREATE INDEX IF NOT EXISTS idx_packages_event_id ON packages(event_id);
CREATE INDEX IF NOT EXISTS idx_packages_event_id_visible ON packages(event_id, visible);

-- PaymentsRepository queries
CREATE INDEX IF NOT EXISTS idx_payments_package_id ON payments(package_id);
CREATE INDEX IF NOT EXISTS idx_payments_reference_id ON payments(reference_id);
CREATE INDEX IF NOT EXISTS idx_payments_transaction_id ON payments(transaction_id);
CREATE INDEX IF NOT EXISTS idx_payments_status ON payments(status);
CREATE INDEX IF NOT EXISTS idx_payments_status_payment_date ON payments(status, payment_date);
CREATE INDEX IF NOT EXISTS idx_payments_payment_date_desc ON payments(payment_date DESC);

-- PhotoLikeRepository queries
CREATE INDEX IF NOT EXISTS idx_photo_likes_user_id_event_photo_id ON photo_likes(user_id, event_photo_id);
CREATE INDEX IF NOT EXISTS idx_photo_likes_event_photo_id ON photo_likes(event_photo_id);

-- UserRepository queries
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);

-- UserProfileRepository queries
CREATE INDEX IF NOT EXISTS idx_users_profile_user_id ON users_profile(user_id);

-- EmailVerificationTokenRepository queries
CREATE INDEX IF NOT EXISTS idx_email_verification_tokens_token ON email_verification_tokens(token);

-- Counters table queries (if used)
CREATE INDEX IF NOT EXISTS idx_counters_album_id ON counters(album_id);
