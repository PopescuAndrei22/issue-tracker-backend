INSERT INTO users (username, email, password, role, created_at, updated_at) VALUES 
('alice_admin', 'alice@example.com', 'password', 'ADMIN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('bob_dev', 'bob@example.com', 'password', 'USER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('charlie_tester', 'charlie@example.com', 'password', 'USER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO projects (name, description, owner_id, created_at, updated_at) VALUES 
('Titanium Mobile App', 'Developing the new cross-platform app', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO issues (title, description, type, priority, status, reporter_id, assignee_id, project_id, created_at, updated_at) VALUES 
('Login screen crash', 'Crashes on iOS 17', 'BUG', 'HIGH', 'IN_PROGRESS', 3, 2, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Add dark mode', 'Users want dark theme', 'FEATURE', 'MEDIUM', 'OPEN', 1, 2, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('API Timeout', 'Timeout on /auth endpoint', 'BUG', 'HIGH', 'IN_PROGRESS', 2, 2, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Update logo', 'New branding assets', 'TASK', 'LOW', 'OPEN', 1, NULL, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Fix spacing', 'Button padding is off', 'BUG', 'LOW', 'IN_PROGRESS', 3, 2, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Export to PDF', 'Requirement from stakeholders', 'FEATURE', 'MEDIUM', 'OPEN', 1, NULL, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Memory leak', 'Found in background sync', 'BUG', 'HIGH', 'IN_PROGRESS', 2, 2, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Refactor Auth', 'Cleanup old code', 'TASK', 'MEDIUM', 'OPEN', 1, NULL, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Add unit tests', 'Coverage is below 20%', 'TASK', 'HIGH', 'OPEN', 3, 2, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Fix typo', 'Header says Welcomee', 'BUG', 'LOW', 'OPEN', 1, 2, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Deep links', 'Support universal links', 'FEATURE', 'MEDIUM', 'OPEN', 1, NULL, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Firebase setup', 'Integrate push notifications', 'TASK', 'HIGH', 'IN_PROGRESS', 3, 2, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);