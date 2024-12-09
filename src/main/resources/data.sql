INSERT INTO albums(id, title, city, event_date, thumb_url, url)
VALUES (1, 'Baile do Sorriso', 'Joinville/SC', '2022-05-21', '/1_baile_sorriso_bc.jpg', 'https://1drv.ms/f/s!AnITpg7s5sZ_hrFh1ZnHSPZhjixaxw');

INSERT INTO albums(id, title, city, event_date, thumb_url, url)
VALUES (2, 'Esquenta Samba Elite Congress 2022', 'Balneário Camboriú/SC', '2022-06-04', '/2_esquenta_elite2022.jpg',
        'https://1drv.ms/f/s!AnITpg7s5sZ_hvIEDyRKib4iQpMzIw');

INSERT INTO albums(id, title, city, event_date, thumb_url, url)
VALUES (3, 'Esquenta Samba Porão da Nêga', 'Joinville/SC', '2022-08-06', '/3_esquenta_porao2022.jpg',
        'https://1drv.ms/f/s!AnITpg7s5sZ_h4tcDplxXkpXFQb6KA');

INSERT INTO albums(id, title, city, event_date, thumb_url, url)
VALUES (4, 'Samba Elite Congress 2022', 'Balneário Camboriú/SC', '2022-08-19', '/4_elite_samba2022.jpg',
        'https://1drv.ms/f/s!AnITpg7s5sZ_lK5qdePzfe4NFlOj_w');

INSERT INTO albums(id, title, city, event_date, thumb_url, url)
VALUES (5, 'ConecDansa 2022', 'Curitiba/PR', '2022-10-15', '/5_conecdansa2022.jpg', 'https://1drv.ms/f/s!AnITpg7s5sZ_lK5r6YcuEzX2Vs4YCw');

INSERT INTO albums(id, title, city, event_date, thumb_url, url)
VALUES (6, 'Elite Samba Congress 2023', 'Balneário Camboriú/SC', '2023-08-18', '/6_elite_2023.jpg',
        'https://1drv.ms/f/s!AnITpg7s5sZ_lK5slwF5cTHiZ6U_hQ');

INSERT INTO albums(id, title, city, event_date, thumb_url, url)
VALUES (8, 'Zouk na Praça', 'Curitiba/PR', '2023-11-26', '/8_zouk_praca.jpg', 'https://1drv.ms/f/s!AnITpg7s5sZ_jPAcpsq2294gPGdAjA');

INSERT INTO albums(id, title, city, event_date, thumb_url, url)
VALUES (9, 'ConecMotion 2024', 'Curitiba/PR', '2024-04-12', '/9_conecmotion2024.jpg', 'https://1drv.ms/f/s!AnITpg7s5sZ_jepCgiryodsQ2TDV8A');

INSERT INTO albums(id, title, city, event_date, thumb_url, url)
VALUES (10, 'Sul in Zouk and Lambada 2024', 'Balneário Camboriú/SC', '2024-05-18', '/10_sul_in_zouk2024.jpg',
        'https://1drv.ms/f/s!AnITpg7s5sZ_jtZs9W2iMru_q7GkeQ');

INSERT INTO albums(id, title, city, event_date, thumb_url, url)
VALUES (11, 'Elite Samba Congress 2024', 'Balneário Camboriú/SC', '2024-08-16', '/11_elite2024.jpg',
        'https://1drv.ms/f/s!AnITpg7s5sZ_kO5xyt2FJ7t4M8fbUA');

INSERT INTO albums(id, title, city, event_date, thumb_url, url)
VALUES (12, 'Zouk Lovers 2024', 'Curitiba/PR', '2024-08-23', '/12_zouk_lovers2024.jpg', 'https://1drv.ms/f/s!AnITpg7s5sZ_k7pSPEefPrvgxx9kwg');

INSERT INTO albums(id, title, city, event_date, thumb_url, url)
VALUES (13, 'Zouk`n beach 2024', 'Balneário Camboriú/SC', '2024-11-14', '/zouk-in-beach-photo-album.jpg',
        'https://1drv.ms/f/s!AnITpg7s5sZ_lK8sLYj9Dq6LG_QW0Q?e=gwmXiu');

INSERT INTO counters(id, album_id, count)
VALUES (1, 1, 15);

INSERT INTO counters(id, album_id, count)
VALUES (2, 2, 7);

INSERT INTO counters(id, album_id, count)
VALUES (3, 3, 3);

INSERT INTO counters(id, album_id, count)
VALUES (4, 4, 11);

INSERT INTO counters(id, album_id, count)
VALUES (5, 5, 9);

INSERT INTO counters(id, album_id, count)
VALUES (6, 6, 8);

INSERT INTO counters(id, album_id, count)
VALUES (8, 8, 11);

INSERT INTO counters(id, album_id, count)
VALUES (9, 9, 3);

INSERT INTO counters(id, album_id, count)
VALUES (10, 10, 20);

INSERT INTO counters(id, album_id, count)
VALUES (11, 11, 20);

INSERT INTO counters(id, album_id, count)
VALUES (12, 12, 54);

INSERT INTO counters(id, album_id, count)
VALUES (13, 13, 1331);

-- social media
INSERT INTO social_media(id, instagram, phone_number) VALUES (1, 'conecdansa', '41984125046');

INSERT INTO social_media(id, instagram, phone_number) VALUES (2, 'elitesamba_congress_bc', '4796610282');

-- events
INSERT INTO events(id, title, description, date, location, cover_url, social_media_id)
VALUES (1, 'ConecSamba 2025', 'ConecSamba é um dos maiores congressos de samba do Paraná', '2025-04-11', 'Curitiba/PR', '/next-events/conecsamba2025.jpg', 1);

INSERT INTO events(id, title, description, date, location, cover_url, social_media_id)
VALUES (2, 'ConecZouk 2025', 'ConecZouk - Gerações do zouk conectando o mundo', '2025-09-12', 'Curitiba/PR', '/next-events/coneczouk2025.jpg', 1);

INSERT INTO events(id, title, description, date, location, cover_url, social_media_id)
VALUES (3, 'Elite Samba Congress 2025', 'Elite Samba Congress - Nosso SAMBA é INFINITO!', '2025-08-15', 'Balneário Camboriú/SC', '/next-events/elitesambacongress2025.jpg', 2);
