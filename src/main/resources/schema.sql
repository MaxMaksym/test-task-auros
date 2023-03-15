CREATE SCHEMA IF NOT EXISTS `auros` DEFAULT CHARACTER SET utf8;
USE `auros`;

DROP TABLE IF EXISTS `k_packs_k_pack_sets`;
DROP TABLE IF EXISTS `k_packs`;
DROP TABLE IF EXISTS `k_pack_sets`;

CREATE TABLE `k_packs`
(
    `id`          BIGINT(11)    NOT NULL AUTO_INCREMENT,
    `title`       VARCHAR(250)  NOT NULL,
    `description` VARCHAR(2000) NOT NULL,
    `date`        DATE          NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `k_pack_sets`
(
    `id`    BIGINT(11)   NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(250) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `k_packs_k_pack_sets`
(
    `k_pack_id`     bigint NOT NULL,
    `k_pack_set_id` bigint NOT NULL,
    KEY `k_packs_k_pack_sets_k_pack_id` (`k_pack_id`),
    KEY `k_packs_k_pack_sets_k_pack_set_id` (`k_pack_set_id`),
    CONSTRAINT `k_packs_k_pack_sets_k_pack_id_fk` FOREIGN KEY (`k_pack_id`) REFERENCES `k_packs` (`id`),
    CONSTRAINT `k_packs_k_pack_sets_k_pack_set_id_fk` FOREIGN KEY (`k_pack_set_id`) REFERENCES `k_pack_sets` (`id`)
);
