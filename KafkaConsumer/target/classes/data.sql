-- Seed login-capable students; password is BCrypt-hashed to match SecurityConfig's PasswordEncoder
INSERT INTO student (name, age, address, password) VALUES ('singam', 25, 'Chennai', '$2a$10$.U8NhpkxujU08By3ERoFv.sn6iC.pZbrn86wZ4K4y5tslwrFA5dge');
INSERT INTO student (name, age, address, password) VALUES ('admin', 30, 'Bangalore', '$2a$10$.DFxfrrgOH3K.ToLheSq4uSi4sjZLPi9FIMR3M0noVNWoyqsP1tqi');
