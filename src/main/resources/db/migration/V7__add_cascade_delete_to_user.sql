ALTER TABLE user_role DROP CONSTRAINT user_role_user_fk,
ADD CONSTRAINT user_role_user_fk FOREIGN KEY (user_) REFERENCES user_ ON DELETE CASCADE;