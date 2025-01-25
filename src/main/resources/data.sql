INSERT INTO albums(id, title, city, event_date, thumb_url, url)
VALUES (1, 'Baile do Sorriso', 'Joinville/SC', '2022-05-21',
        '/1_baile_sorriso_bc.jpg',
        'https://1drv.ms/f/s!AnITpg7s5sZ_hrFh1ZnHSPZhjixaxw');

INSERT INTO albums(id, title, city, event_date, thumb_url, url)
VALUES (2, 'Esquenta Samba Elite Congress 2022', 'Balneário Camboriú/SC',
        '2022-06-04', '/2_esquenta_elite2022.jpg',
        'https://1drv.ms/f/s!AnITpg7s5sZ_hvIEDyRKib4iQpMzIw');

INSERT INTO albums(id, title, city, event_date, thumb_url, url)
VALUES (3, 'Esquenta Samba Porão da Nêga', 'Joinville/SC', '2022-08-06',
        '/3_esquenta_porao2022.jpg',
        'https://1drv.ms/f/s!AnITpg7s5sZ_h4tcDplxXkpXFQb6KA');

INSERT INTO albums(id, title, city, event_date, thumb_url, url)
VALUES (4, 'Samba Elite Congress 2022', 'Balneário Camboriú/SC', '2022-08-19',
        '/4_elite_samba2022.jpg',
        'https://1drv.ms/f/s!AnITpg7s5sZ_lK5qdePzfe4NFlOj_w');

INSERT INTO albums(id, title, city, event_date, thumb_url, url)
VALUES (5, 'ConecDansa 2022', 'Curitiba/PR', '2022-10-15',
        '/5_conecdansa2022.jpg',
        'https://1drv.ms/f/s!AnITpg7s5sZ_lK5r6YcuEzX2Vs4YCw');

INSERT INTO albums(id, title, city, event_date, thumb_url, url)
VALUES (6, 'Elite Samba Congress 2023', 'Balneário Camboriú/SC', '2023-08-18',
        '/6_elite_2023.jpg',
        'https://1drv.ms/f/s!AnITpg7s5sZ_lK5slwF5cTHiZ6U_hQ');

INSERT INTO albums(id, title, city, event_date, thumb_url, url)
VALUES (8, 'Zouk na Praça', 'Curitiba/PR', '2023-11-26', '/8_zouk_praca.jpg',
        'https://1drv.ms/f/s!AnITpg7s5sZ_jPAcpsq2294gPGdAjA');

INSERT INTO albums(id, title, city, event_date, thumb_url, url)
VALUES (9, 'ConecMotion 2024', 'Curitiba/PR', '2024-04-12',
        '/9_conecmotion2024.jpg',
        'https://1drv.ms/f/s!AnITpg7s5sZ_jepCgiryodsQ2TDV8A');

INSERT INTO albums(id, title, city, event_date, thumb_url, url)
VALUES (10, 'Sul in Zouk and Lambada 2024', 'Balneário Camboriú/SC',
        '2024-05-18', '/10_sul_in_zouk2024.jpg',
        'https://1drv.ms/f/s!AnITpg7s5sZ_jtZs9W2iMru_q7GkeQ');

INSERT INTO albums(id, title, city, event_date, thumb_url, url)
VALUES (11, 'Elite Samba Congress 2024', 'Balneário Camboriú/SC', '2024-08-16',
        '/11_elite2024.jpg',
        'https://1drv.ms/f/s!AnITpg7s5sZ_kO5xyt2FJ7t4M8fbUA');

INSERT INTO albums(id, title, city, event_date, thumb_url, url)
VALUES (12, 'Zouk Lovers 2024', 'Curitiba/PR', '2024-08-23',
        '/12_zouk_lovers2024.jpg',
        'https://1drv.ms/f/s!AnITpg7s5sZ_k7pSPEefPrvgxx9kwg');

INSERT INTO albums(id, title, city, event_date, thumb_url, url)
VALUES (13, 'Zouk`n beach 2024', 'Balneário Camboriú/SC', '2024-11-14',
        '/zouk-in-beach-photo-album.jpg',
        'https://1drv.ms/f/s!AnITpg7s5sZ_lK8sLYj9Dq6LG_QW0Q?e=gwmXiu');

INSERT INTO counters(id, album_id, count)
VALUES (1, 1, 19);

INSERT INTO counters(id, album_id, count)
VALUES (2, 2, 9);

INSERT INTO counters(id, album_id, count)
VALUES (3, 3, 3);

INSERT INTO counters(id, album_id, count)
VALUES (4, 4, 11);

INSERT INTO counters(id, album_id, count)
VALUES (5, 5, 11);

INSERT INTO counters(id, album_id, count)
VALUES (6, 6, 13);

INSERT INTO counters(id, album_id, count)
VALUES (8, 8, 14);

INSERT INTO counters(id, album_id, count)
VALUES (9, 9, 3);

INSERT INTO counters(id, album_id, count)
VALUES (10, 10, 24);

INSERT INTO counters(id, album_id, count)
VALUES (11, 11, 22);

INSERT INTO counters(id, album_id, count)
VALUES (12, 12, 57);

INSERT INTO counters(id, album_id, count)
VALUES (13, 13, 1335);

-- events
INSERT INTO events(id, title, description, date, location, cover_url, event_url)
VALUES (1, 'ConecSamba 2025',
        'ConecSamba é um dos maiores congressos de samba do Paraná',
        '2025-04-11', 'Curitiba/PR', '/next-events/conecsamba2025.jpg',
        'conecsamba-2025');

INSERT INTO events(id, title, description, date, location, cover_url, event_url)
VALUES (2, 'ConecZouk 2025', 'ConecZouk - Gerações do zouk conectando o mundo',
        '2025-09-12', 'Curitiba/PR', '/next-events/coneczouk2025.jpg',
        'coneczouk-2025');

INSERT INTO events(id, title, description, date, location, cover_url, event_url)
VALUES (3, 'Elite Samba Congress 2025',
        'Elite Samba Congress - Nosso SAMBA é INFINITO!', '2025-08-15',
        'Balneário Camboriú/SC', '/next-events/elitesambacongress2025.jpg',
        'elitesamba-congress-2025');

-- social media
INSERT INTO social_media(id, event_id, instagram, phone_number)
VALUES (1, 1, 'conecdansa', '41984125046');
INSERT INTO social_media(id, event_id, instagram, phone_number)
VALUES (3, 2, 'conecdansa', '41984125046');
INSERT INTO social_media(id, event_id, instagram, phone_number)
VALUES (2, 3, 'elitesamba_congress_bc', '4796610282');

-- photos
INSERT INTO PHOTOS(id, event_id, image_path)
VALUES (1, 1, '/next-events/conecdansa-2022/conecdansa-2022-1.jpg');
INSERT INTO PHOTOS(id, event_id, image_path)
VALUES (2, 1, '/next-events/conecdansa-2022/conecdansa-2022-2.jpg');
INSERT INTO PHOTOS(id, event_id, image_path)
VALUES (3, 1, '/next-events/conecdansa-2022/conecdansa-2022-3.jpg');

-- Elite samba congress 2024
INSERT INTO PHOTOS(id, event_id, image_path)
VALUES (4, 3,
        '/next-events/elite-samba-congress-2024/elite_samba_congress2024-1.jpg');
INSERT INTO PHOTOS(id, event_id, image_path)
VALUES (5, 3,
        '/next-events/elite-samba-congress-2024/elite_samba_congress2024-2.jpg');
INSERT INTO PHOTOS(id, event_id, image_path)
VALUES (6, 3,
        '/next-events/elite-samba-congress-2024/elite_samba_congress2024-3.jpg');

-- event Album test
INSERT INTO SUB_EVENTS(id, event_id, name)
VALUES (1, 1, 'baile-primeiro-dia');
INSERT INTO EVENT_PHOTOS(id, sub_event_id, image_path)
VALUES (1, 1, '/next-events/conecdansa-2022/conecdansa-2022-1.jpg');
INSERT INTO EVENT_PHOTOS(id, sub_event_id, image_path)
VALUES (2, 1, '/next-events/conecdansa-2022/conecdansa-2022-2.jpg');
INSERT INTO EVENT_PHOTOS(id, sub_event_id, image_path)
VALUES (3, 1, '/next-events/conecdansa-2022/conecdansa-2022-3.jpg');

INSERT INTO SUB_EVENTS(id, event_id, name)
VALUES (2, 1, 'baile-segundo-dia');
INSERT INTO EVENT_PHOTOS(id, sub_event_id, image_path)
VALUES (4, 2, '/next-events/conecdansa-2022/conecdansa-2022-1.jpg');
