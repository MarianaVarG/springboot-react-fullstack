INSERT INTO exposures(id, type) VALUES
    (1, 'Private'),
    (2, 'Public')
    ON CONFLICT DO NOTHING;