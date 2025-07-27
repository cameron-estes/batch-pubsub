-- Create the messages table
CREATE TABLE IF NOT EXISTS messages (
    id INT AUTO_INCREMENT PRIMARY KEY,
    message VARCHAR(255) NOT NULL
);

-- Insert sample data
INSERT INTO messages (message) VALUES ('Chaos Reigns');
INSERT INTO messages (message) VALUES ('I am the one who knocks.'); 