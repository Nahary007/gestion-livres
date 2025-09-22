-- Insertion des auteurs
INSERT INTO authors (first_name, last_name, email, birth_date, biography) VALUES
('Victor', 'Hugo', 'victor.hugo@email.com', '1802-02-26', 'Poète, dramaturge et prosateur romantique français.'),
('Marcel', 'Proust', 'marcel.proust@email.com', '1871-07-10', 'Écrivain français, auteur de "À la recherche du temps perdu".'),
('Albert', 'Camus', 'albert.camus@email.com', '1913-11-07', 'Écrivain, philosophe, romancier et nouvelliste français.'),
('Simone', 'de Beauvoir', 'simone.beauvoir@email.com', '1908-01-09', 'Philosophe, romancière, mémorialiste et essayiste française.');

-- Insertion des livres
INSERT INTO books (title, isbn, price, page_count, publication_date, author_id, status, description) VALUES
('Les Misérables', '978-2-07-040922-8', 12.50, 1488, '1862-01-01', 1, 'AVAILABLE', 'Roman social et historique de Victor Hugo.'),
('Notre-Dame de Paris', '978-2-07-041326-3', 9.90, 940, '1831-01-01', 1, 'AVAILABLE', 'Roman historique de Victor Hugo.'),
('À la recherche du temps perdu', '978-2-07-010431-3', 45.00, 4211, '1913-01-01', 2, 'BORROWED', 'Cycle romanesque de Marcel Proust.'),
('L''Étranger', '978-2-07-036002-4', 7.50, 184, '1942-01-01', 3, 'AVAILABLE', 'Roman philosophique d''Albert Camus.'),
('La Peste', '978-2-07-036042-0', 8.90, 347, '1947-01-01', 3, 'RESERVED', 'Roman allégorique d''Albert Camus.'),
('Le Deuxième Sexe', '978-2-07-032351-7', 14.90, 408, '1949-01-01', 4, 'AVAILABLE', 'Ouvrage philosophique et féministe de Simone de Beauvoir.');