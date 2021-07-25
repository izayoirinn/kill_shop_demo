/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50527
Source Host           : localhost:3306
Source Database       : redo_kill_shop

Target Server Type    : MYSQL
Target Server Version : 50527
File Encoding         : 65001

Date: 2021-07-23 14:40:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `goods`
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `goods_id` int(11) NOT NULL AUTO_INCREMENT,
  `goods_name` varchar(255) DEFAULT NULL,
  `goods_img` varchar(255) DEFAULT NULL,
  `goods_content` varchar(255) DEFAULT NULL,
  `goods_price` decimal(8,2) DEFAULT NULL,
  `goods_stock` int(11) DEFAULT NULL,
  PRIMARY KEY (`goods_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES ('1', '可乐', 'shop_001.jpg', '可乐可乐', '5.00', '900');
INSERT INTO `goods` VALUES ('2', '薯片', 'shop_002.jpg', '薯片薯片', '8.00', '1000');
INSERT INTO `goods` VALUES ('3', '妙脆角', 'shop_003.jpg', '妙脆角', '6.00', '800');
INSERT INTO `goods` VALUES ('4', '卫龙辣条', '767b6a3b-9974-4238-bac5-70c0c3a76ff4.jpg', '卫龙辣条,好吃的亚比', '0.50', '700');
INSERT INTO `goods` VALUES ('5', '菠萝蜜', '98808807-8e21-4977-a298-13707203ccdf.png', '甜甜脆脆的菠萝蜜', '50.00', '100');
INSERT INTO `goods` VALUES ('6', '哈密瓜', '00f29b13-abf2-4b6c-9f47-b7fe6a9cc824.png', '包甜哈密瓜', '45.00', '200');

-- ----------------------------
-- Table structure for `kill_goods`
-- ----------------------------
DROP TABLE IF EXISTS `kill_goods`;
CREATE TABLE `kill_goods` (
  `kill_id` int(11) NOT NULL AUTO_INCREMENT,
  `goods_id` int(11) DEFAULT NULL,
  `stock_count` int(11) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `kill_price` decimal(11,2) DEFAULT NULL,
  PRIMARY KEY (`kill_id`) USING BTREE,
  KEY `goods_id` (`goods_id`) USING BTREE,
  CONSTRAINT `killgoods_ibfk_1` FOREIGN KEY (`goods_id`) REFERENCES `goods` (`goods_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of kill_goods
-- ----------------------------
INSERT INTO `kill_goods` VALUES ('1', '1', '100', '2021-07-14 10:14:35', '2021-07-15 15:14:35', '1.00');
INSERT INTO `kill_goods` VALUES ('2', '2', '100', '2021-02-10 10:14:35', '2021-03-10 10:14:35', '1.00');
INSERT INTO `kill_goods` VALUES ('3', '3', '98', '2021-07-13 08:44:56', '2021-08-27 08:45:01', '3.00');
INSERT INTO `kill_goods` VALUES ('4', '4', '0', '2021-07-02 08:46:00', '2021-08-26 08:46:06', '0.30');

-- ----------------------------
-- Table structure for `kill_order_info`
-- ----------------------------
DROP TABLE IF EXISTS `kill_order_info`;
CREATE TABLE `kill_order_info` (
  `kill_order_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `kill_id` int(11) DEFAULT NULL,
  `order_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`kill_order_id`) USING BTREE,
  KEY `user_id` (`user_id`) USING BTREE,
  KEY `order_id` (`order_id`) USING BTREE,
  CONSTRAINT `kill_order_info_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`user_id`),
  CONSTRAINT `kill_order_info_ibfk_3` FOREIGN KEY (`order_id`) REFERENCES `order_info` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2130 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of kill_order_info
-- ----------------------------
INSERT INTO `kill_order_info` VALUES ('1929', '10', '4', '1939');
INSERT INTO `kill_order_info` VALUES ('1930', '10', '4', '1940');
INSERT INTO `kill_order_info` VALUES ('1931', '10', '3', '1941');
INSERT INTO `kill_order_info` VALUES ('1932', '1', '3', '1942');
INSERT INTO `kill_order_info` VALUES ('1933', '1', '3', '1943');
INSERT INTO `kill_order_info` VALUES ('1934', '1', '3', '1944');
INSERT INTO `kill_order_info` VALUES ('1935', '1', '3', '1945');
INSERT INTO `kill_order_info` VALUES ('1936', '1', '3', '1946');
INSERT INTO `kill_order_info` VALUES ('1937', '1', '3', '1947');
INSERT INTO `kill_order_info` VALUES ('1938', '1', '3', '1948');
INSERT INTO `kill_order_info` VALUES ('1939', '1', '3', '1949');
INSERT INTO `kill_order_info` VALUES ('1940', '1', '3', '1950');
INSERT INTO `kill_order_info` VALUES ('1941', '1', '3', '1951');
INSERT INTO `kill_order_info` VALUES ('1942', '1', '3', '1952');
INSERT INTO `kill_order_info` VALUES ('1943', '1', '3', '1953');
INSERT INTO `kill_order_info` VALUES ('1944', '1', '3', '1954');
INSERT INTO `kill_order_info` VALUES ('1945', '1', '3', '1955');
INSERT INTO `kill_order_info` VALUES ('1946', '1', '3', '1956');
INSERT INTO `kill_order_info` VALUES ('1947', '1', '3', '1957');
INSERT INTO `kill_order_info` VALUES ('1948', '1', '3', '1958');
INSERT INTO `kill_order_info` VALUES ('1949', '1', '3', '1959');
INSERT INTO `kill_order_info` VALUES ('1950', '1', '3', '1960');
INSERT INTO `kill_order_info` VALUES ('1951', '1', '3', '1961');
INSERT INTO `kill_order_info` VALUES ('1952', '1', '3', '1962');
INSERT INTO `kill_order_info` VALUES ('1953', '1', '3', '1963');
INSERT INTO `kill_order_info` VALUES ('1954', '1', '3', '1964');
INSERT INTO `kill_order_info` VALUES ('1955', '1', '3', '1965');
INSERT INTO `kill_order_info` VALUES ('1956', '1', '3', '1966');
INSERT INTO `kill_order_info` VALUES ('1957', '1', '3', '1967');
INSERT INTO `kill_order_info` VALUES ('1958', '1', '3', '1968');
INSERT INTO `kill_order_info` VALUES ('1959', '1', '3', '1969');
INSERT INTO `kill_order_info` VALUES ('1960', '1', '3', '1970');
INSERT INTO `kill_order_info` VALUES ('1961', '1', '3', '1971');
INSERT INTO `kill_order_info` VALUES ('1962', '1', '3', '1972');
INSERT INTO `kill_order_info` VALUES ('1963', '1', '3', '1973');
INSERT INTO `kill_order_info` VALUES ('1964', '1', '3', '1974');
INSERT INTO `kill_order_info` VALUES ('1965', '1', '3', '1975');
INSERT INTO `kill_order_info` VALUES ('1966', '1', '3', '1976');
INSERT INTO `kill_order_info` VALUES ('1967', '1', '3', '1977');
INSERT INTO `kill_order_info` VALUES ('1968', '1', '3', '1978');
INSERT INTO `kill_order_info` VALUES ('1969', '1', '3', '1979');
INSERT INTO `kill_order_info` VALUES ('1970', '1', '3', '1980');
INSERT INTO `kill_order_info` VALUES ('1971', '1', '3', '1981');
INSERT INTO `kill_order_info` VALUES ('1972', '1', '3', '1982');
INSERT INTO `kill_order_info` VALUES ('1973', '1', '3', '1983');
INSERT INTO `kill_order_info` VALUES ('1974', '1', '3', '1984');
INSERT INTO `kill_order_info` VALUES ('1975', '1', '3', '1985');
INSERT INTO `kill_order_info` VALUES ('1976', '1', '3', '1986');
INSERT INTO `kill_order_info` VALUES ('1977', '1', '3', '1987');
INSERT INTO `kill_order_info` VALUES ('1978', '1', '3', '1988');
INSERT INTO `kill_order_info` VALUES ('1979', '1', '3', '1989');
INSERT INTO `kill_order_info` VALUES ('1980', '1', '3', '1990');
INSERT INTO `kill_order_info` VALUES ('1981', '1', '3', '1991');
INSERT INTO `kill_order_info` VALUES ('1982', '1', '3', '1992');
INSERT INTO `kill_order_info` VALUES ('1983', '1', '3', '1993');
INSERT INTO `kill_order_info` VALUES ('1984', '1', '3', '1994');
INSERT INTO `kill_order_info` VALUES ('1985', '1', '3', '1995');
INSERT INTO `kill_order_info` VALUES ('1986', '1', '3', '1996');
INSERT INTO `kill_order_info` VALUES ('1987', '1', '3', '1997');
INSERT INTO `kill_order_info` VALUES ('1988', '1', '3', '1998');
INSERT INTO `kill_order_info` VALUES ('1989', '1', '3', '1999');
INSERT INTO `kill_order_info` VALUES ('1990', '1', '3', '2000');
INSERT INTO `kill_order_info` VALUES ('1991', '1', '3', '2001');
INSERT INTO `kill_order_info` VALUES ('1992', '1', '3', '2002');
INSERT INTO `kill_order_info` VALUES ('1993', '1', '3', '2003');
INSERT INTO `kill_order_info` VALUES ('1994', '1', '3', '2004');
INSERT INTO `kill_order_info` VALUES ('1995', '1', '3', '2005');
INSERT INTO `kill_order_info` VALUES ('1996', '1', '3', '2006');
INSERT INTO `kill_order_info` VALUES ('1997', '1', '3', '2007');
INSERT INTO `kill_order_info` VALUES ('1998', '1', '3', '2008');
INSERT INTO `kill_order_info` VALUES ('1999', '1', '3', '2009');
INSERT INTO `kill_order_info` VALUES ('2000', '1', '3', '2010');
INSERT INTO `kill_order_info` VALUES ('2001', '1', '3', '2011');
INSERT INTO `kill_order_info` VALUES ('2002', '1', '3', '2012');
INSERT INTO `kill_order_info` VALUES ('2003', '1', '3', '2013');
INSERT INTO `kill_order_info` VALUES ('2004', '1', '3', '2014');
INSERT INTO `kill_order_info` VALUES ('2005', '1', '3', '2015');
INSERT INTO `kill_order_info` VALUES ('2006', '1', '3', '2016');
INSERT INTO `kill_order_info` VALUES ('2007', '1', '3', '2017');
INSERT INTO `kill_order_info` VALUES ('2008', '1', '3', '2018');
INSERT INTO `kill_order_info` VALUES ('2009', '1', '3', '2019');
INSERT INTO `kill_order_info` VALUES ('2010', '1', '3', '2020');
INSERT INTO `kill_order_info` VALUES ('2011', '1', '3', '2021');
INSERT INTO `kill_order_info` VALUES ('2012', '1', '3', '2022');
INSERT INTO `kill_order_info` VALUES ('2013', '1', '3', '2023');
INSERT INTO `kill_order_info` VALUES ('2014', '1', '3', '2024');
INSERT INTO `kill_order_info` VALUES ('2015', '1', '3', '2025');
INSERT INTO `kill_order_info` VALUES ('2016', '1', '3', '2026');
INSERT INTO `kill_order_info` VALUES ('2017', '1', '3', '2027');
INSERT INTO `kill_order_info` VALUES ('2018', '1', '3', '2028');
INSERT INTO `kill_order_info` VALUES ('2019', '1', '3', '2029');
INSERT INTO `kill_order_info` VALUES ('2020', '1', '3', '2030');
INSERT INTO `kill_order_info` VALUES ('2021', '1', '3', '2031');
INSERT INTO `kill_order_info` VALUES ('2022', '1', '3', '2032');
INSERT INTO `kill_order_info` VALUES ('2023', '1', '3', '2033');
INSERT INTO `kill_order_info` VALUES ('2024', '1', '3', '2034');
INSERT INTO `kill_order_info` VALUES ('2025', '1', '3', '2035');
INSERT INTO `kill_order_info` VALUES ('2026', '1', '3', '2036');
INSERT INTO `kill_order_info` VALUES ('2027', '1', '3', '2037');
INSERT INTO `kill_order_info` VALUES ('2028', '1', '3', '2038');
INSERT INTO `kill_order_info` VALUES ('2029', '1', '3', '2039');
INSERT INTO `kill_order_info` VALUES ('2030', '1', '3', '2040');
INSERT INTO `kill_order_info` VALUES ('2031', '1', '3', '2041');
INSERT INTO `kill_order_info` VALUES ('2032', '1', '3', '2042');
INSERT INTO `kill_order_info` VALUES ('2033', '1', '3', '2043');
INSERT INTO `kill_order_info` VALUES ('2034', '1', '3', '2044');
INSERT INTO `kill_order_info` VALUES ('2035', '1', '3', '2045');
INSERT INTO `kill_order_info` VALUES ('2036', '1', '3', '2046');
INSERT INTO `kill_order_info` VALUES ('2037', '1', '3', '2047');
INSERT INTO `kill_order_info` VALUES ('2038', '1', '3', '2048');
INSERT INTO `kill_order_info` VALUES ('2039', '1', '3', '2049');
INSERT INTO `kill_order_info` VALUES ('2040', '1', '3', '2050');
INSERT INTO `kill_order_info` VALUES ('2041', '1', '3', '2051');
INSERT INTO `kill_order_info` VALUES ('2042', '1', '3', '2052');
INSERT INTO `kill_order_info` VALUES ('2043', '1', '3', '2053');
INSERT INTO `kill_order_info` VALUES ('2044', '1', '3', '2054');
INSERT INTO `kill_order_info` VALUES ('2045', '1', '3', '2055');
INSERT INTO `kill_order_info` VALUES ('2046', '1', '3', '2056');
INSERT INTO `kill_order_info` VALUES ('2047', '1', '3', '2057');
INSERT INTO `kill_order_info` VALUES ('2048', '1', '3', '2058');
INSERT INTO `kill_order_info` VALUES ('2049', '1', '3', '2059');
INSERT INTO `kill_order_info` VALUES ('2050', '1', '3', '2060');
INSERT INTO `kill_order_info` VALUES ('2051', '1', '3', '2061');
INSERT INTO `kill_order_info` VALUES ('2052', '1', '3', '2062');
INSERT INTO `kill_order_info` VALUES ('2053', '1', '3', '2063');
INSERT INTO `kill_order_info` VALUES ('2054', '1', '3', '2064');
INSERT INTO `kill_order_info` VALUES ('2055', '1', '3', '2065');
INSERT INTO `kill_order_info` VALUES ('2056', '1', '3', '2066');
INSERT INTO `kill_order_info` VALUES ('2057', '1', '3', '2067');
INSERT INTO `kill_order_info` VALUES ('2058', '1', '3', '2068');
INSERT INTO `kill_order_info` VALUES ('2059', '1', '3', '2069');
INSERT INTO `kill_order_info` VALUES ('2060', '1', '3', '2070');
INSERT INTO `kill_order_info` VALUES ('2061', '1', '3', '2071');
INSERT INTO `kill_order_info` VALUES ('2062', '1', '3', '2072');
INSERT INTO `kill_order_info` VALUES ('2063', '1', '3', '2073');
INSERT INTO `kill_order_info` VALUES ('2064', '1', '3', '2074');
INSERT INTO `kill_order_info` VALUES ('2065', '1', '3', '2075');
INSERT INTO `kill_order_info` VALUES ('2066', '1', '3', '2076');
INSERT INTO `kill_order_info` VALUES ('2067', '1', '3', '2077');
INSERT INTO `kill_order_info` VALUES ('2068', '1', '3', '2078');
INSERT INTO `kill_order_info` VALUES ('2069', '1', '3', '2079');
INSERT INTO `kill_order_info` VALUES ('2070', '1', '3', '2080');
INSERT INTO `kill_order_info` VALUES ('2071', '1', '3', '2081');
INSERT INTO `kill_order_info` VALUES ('2072', '1', '3', '2082');
INSERT INTO `kill_order_info` VALUES ('2073', '1', '3', '2083');
INSERT INTO `kill_order_info` VALUES ('2074', '1', '3', '2084');
INSERT INTO `kill_order_info` VALUES ('2075', '1', '3', '2085');
INSERT INTO `kill_order_info` VALUES ('2076', '1', '3', '2086');
INSERT INTO `kill_order_info` VALUES ('2077', '1', '3', '2087');
INSERT INTO `kill_order_info` VALUES ('2078', '1', '3', '2088');
INSERT INTO `kill_order_info` VALUES ('2079', '1', '3', '2089');
INSERT INTO `kill_order_info` VALUES ('2080', '1', '3', '2090');
INSERT INTO `kill_order_info` VALUES ('2081', '1', '3', '2091');
INSERT INTO `kill_order_info` VALUES ('2082', '1', '3', '2092');
INSERT INTO `kill_order_info` VALUES ('2083', '1', '3', '2093');
INSERT INTO `kill_order_info` VALUES ('2084', '1', '3', '2094');
INSERT INTO `kill_order_info` VALUES ('2085', '1', '3', '2095');
INSERT INTO `kill_order_info` VALUES ('2086', '1', '3', '2096');
INSERT INTO `kill_order_info` VALUES ('2087', '1', '3', '2097');
INSERT INTO `kill_order_info` VALUES ('2088', '1', '3', '2098');
INSERT INTO `kill_order_info` VALUES ('2089', '1', '3', '2099');
INSERT INTO `kill_order_info` VALUES ('2090', '1', '3', '2100');
INSERT INTO `kill_order_info` VALUES ('2091', '1', '3', '2101');
INSERT INTO `kill_order_info` VALUES ('2092', '1', '3', '2102');
INSERT INTO `kill_order_info` VALUES ('2093', '1', '3', '2103');
INSERT INTO `kill_order_info` VALUES ('2094', '1', '3', '2104');
INSERT INTO `kill_order_info` VALUES ('2095', '1', '3', '2105');
INSERT INTO `kill_order_info` VALUES ('2096', '1', '3', '2106');
INSERT INTO `kill_order_info` VALUES ('2097', '1', '3', '2107');
INSERT INTO `kill_order_info` VALUES ('2098', '1', '3', '2108');
INSERT INTO `kill_order_info` VALUES ('2099', '1', '3', '2109');
INSERT INTO `kill_order_info` VALUES ('2100', '1', '3', '2110');
INSERT INTO `kill_order_info` VALUES ('2101', '1', '3', '2111');
INSERT INTO `kill_order_info` VALUES ('2102', '1', '3', '2112');
INSERT INTO `kill_order_info` VALUES ('2103', '1', '3', '2113');
INSERT INTO `kill_order_info` VALUES ('2104', '1', '3', '2114');
INSERT INTO `kill_order_info` VALUES ('2105', '1', '3', '2115');
INSERT INTO `kill_order_info` VALUES ('2106', '1', '3', '2116');
INSERT INTO `kill_order_info` VALUES ('2107', '1', '3', '2117');
INSERT INTO `kill_order_info` VALUES ('2108', '1', '3', '2118');
INSERT INTO `kill_order_info` VALUES ('2109', '1', '3', '2119');
INSERT INTO `kill_order_info` VALUES ('2110', '1', '3', '2120');
INSERT INTO `kill_order_info` VALUES ('2111', '1', '3', '2121');
INSERT INTO `kill_order_info` VALUES ('2112', '1', '3', '2122');
INSERT INTO `kill_order_info` VALUES ('2113', '1', '3', '2123');
INSERT INTO `kill_order_info` VALUES ('2114', '1', '3', '2124');
INSERT INTO `kill_order_info` VALUES ('2115', '1', '3', '2125');
INSERT INTO `kill_order_info` VALUES ('2116', '1', '3', '2126');
INSERT INTO `kill_order_info` VALUES ('2117', '1', '3', '2127');
INSERT INTO `kill_order_info` VALUES ('2118', '1', '3', '2128');
INSERT INTO `kill_order_info` VALUES ('2119', '1', '3', '2129');
INSERT INTO `kill_order_info` VALUES ('2120', '1', '3', '2130');
INSERT INTO `kill_order_info` VALUES ('2121', '1', '3', '2131');
INSERT INTO `kill_order_info` VALUES ('2122', '1', '3', '2132');
INSERT INTO `kill_order_info` VALUES ('2123', '1', '3', '2133');
INSERT INTO `kill_order_info` VALUES ('2124', '1', '3', '2134');
INSERT INTO `kill_order_info` VALUES ('2125', '1', '3', '2135');
INSERT INTO `kill_order_info` VALUES ('2126', '1', '3', '2136');
INSERT INTO `kill_order_info` VALUES ('2127', '1', '3', '2137');
INSERT INTO `kill_order_info` VALUES ('2128', '10', '3', '2138');
INSERT INTO `kill_order_info` VALUES ('2129', '10', '3', '2139');

-- ----------------------------
-- Table structure for `order_info`
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `goods_id` int(11) DEFAULT NULL,
  `goods_name` varchar(255) DEFAULT NULL,
  `goods_count` int(11) DEFAULT NULL,
  `goods_price` decimal(11,2) DEFAULT NULL,
  `order_status` int(1) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `pay_date` datetime DEFAULT NULL,
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2140 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of order_info
-- ----------------------------
INSERT INTO `order_info` VALUES ('1939', '10', '4', '卫龙辣条', '1', '0.30', '-1', '2021-07-23 14:11:24', null);
INSERT INTO `order_info` VALUES ('1940', '10', '4', '卫龙辣条', '1', '0.30', '2', '2021-07-23 14:12:47', '2021-07-23 14:13:13');
INSERT INTO `order_info` VALUES ('1941', '10', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:13:28', null);
INSERT INTO `order_info` VALUES ('1942', '1', '3', '妙脆角', '1', '3.00', '2', '2021-07-23 14:14:24', '2021-07-23 14:16:36');
INSERT INTO `order_info` VALUES ('1943', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:24', null);
INSERT INTO `order_info` VALUES ('1944', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:24', null);
INSERT INTO `order_info` VALUES ('1945', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:24', null);
INSERT INTO `order_info` VALUES ('1946', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:24', null);
INSERT INTO `order_info` VALUES ('1947', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:24', null);
INSERT INTO `order_info` VALUES ('1948', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:24', null);
INSERT INTO `order_info` VALUES ('1949', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:24', null);
INSERT INTO `order_info` VALUES ('1950', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:25', null);
INSERT INTO `order_info` VALUES ('1951', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:25', null);
INSERT INTO `order_info` VALUES ('1952', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:25', null);
INSERT INTO `order_info` VALUES ('1953', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:25', null);
INSERT INTO `order_info` VALUES ('1954', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:25', null);
INSERT INTO `order_info` VALUES ('1955', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:25', null);
INSERT INTO `order_info` VALUES ('1956', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:25', null);
INSERT INTO `order_info` VALUES ('1957', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:25', null);
INSERT INTO `order_info` VALUES ('1958', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:25', null);
INSERT INTO `order_info` VALUES ('1959', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:25', null);
INSERT INTO `order_info` VALUES ('1960', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:25', null);
INSERT INTO `order_info` VALUES ('1961', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:25', null);
INSERT INTO `order_info` VALUES ('1962', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:25', null);
INSERT INTO `order_info` VALUES ('1963', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:25', null);
INSERT INTO `order_info` VALUES ('1964', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:25', null);
INSERT INTO `order_info` VALUES ('1965', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:25', null);
INSERT INTO `order_info` VALUES ('1966', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:25', null);
INSERT INTO `order_info` VALUES ('1967', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:25', null);
INSERT INTO `order_info` VALUES ('1968', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:25', null);
INSERT INTO `order_info` VALUES ('1969', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:25', null);
INSERT INTO `order_info` VALUES ('1970', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:26', null);
INSERT INTO `order_info` VALUES ('1971', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:26', null);
INSERT INTO `order_info` VALUES ('1972', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:26', null);
INSERT INTO `order_info` VALUES ('1973', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:26', null);
INSERT INTO `order_info` VALUES ('1974', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:26', null);
INSERT INTO `order_info` VALUES ('1975', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:26', null);
INSERT INTO `order_info` VALUES ('1976', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:26', null);
INSERT INTO `order_info` VALUES ('1977', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:26', null);
INSERT INTO `order_info` VALUES ('1978', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:26', null);
INSERT INTO `order_info` VALUES ('1979', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:26', null);
INSERT INTO `order_info` VALUES ('1980', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:26', null);
INSERT INTO `order_info` VALUES ('1981', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:26', null);
INSERT INTO `order_info` VALUES ('1982', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:26', null);
INSERT INTO `order_info` VALUES ('1983', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:26', null);
INSERT INTO `order_info` VALUES ('1984', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:26', null);
INSERT INTO `order_info` VALUES ('1985', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:26', null);
INSERT INTO `order_info` VALUES ('1986', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:26', null);
INSERT INTO `order_info` VALUES ('1987', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:26', null);
INSERT INTO `order_info` VALUES ('1988', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:26', null);
INSERT INTO `order_info` VALUES ('1989', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:26', null);
INSERT INTO `order_info` VALUES ('1990', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:26', null);
INSERT INTO `order_info` VALUES ('1991', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:26', null);
INSERT INTO `order_info` VALUES ('1992', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:26', null);
INSERT INTO `order_info` VALUES ('1993', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:27', null);
INSERT INTO `order_info` VALUES ('1994', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:27', null);
INSERT INTO `order_info` VALUES ('1995', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:27', null);
INSERT INTO `order_info` VALUES ('1996', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:27', null);
INSERT INTO `order_info` VALUES ('1997', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:27', null);
INSERT INTO `order_info` VALUES ('1998', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:27', null);
INSERT INTO `order_info` VALUES ('1999', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:27', null);
INSERT INTO `order_info` VALUES ('2000', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:27', null);
INSERT INTO `order_info` VALUES ('2001', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:27', null);
INSERT INTO `order_info` VALUES ('2002', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:27', null);
INSERT INTO `order_info` VALUES ('2003', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:27', null);
INSERT INTO `order_info` VALUES ('2004', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:27', null);
INSERT INTO `order_info` VALUES ('2005', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:27', null);
INSERT INTO `order_info` VALUES ('2006', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:27', null);
INSERT INTO `order_info` VALUES ('2007', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:27', null);
INSERT INTO `order_info` VALUES ('2008', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:27', null);
INSERT INTO `order_info` VALUES ('2009', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:27', null);
INSERT INTO `order_info` VALUES ('2010', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:27', null);
INSERT INTO `order_info` VALUES ('2011', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:27', null);
INSERT INTO `order_info` VALUES ('2012', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:27', null);
INSERT INTO `order_info` VALUES ('2013', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:27', null);
INSERT INTO `order_info` VALUES ('2014', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:27', null);
INSERT INTO `order_info` VALUES ('2015', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:27', null);
INSERT INTO `order_info` VALUES ('2016', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:27', null);
INSERT INTO `order_info` VALUES ('2017', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:27', null);
INSERT INTO `order_info` VALUES ('2018', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:27', null);
INSERT INTO `order_info` VALUES ('2019', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:27', null);
INSERT INTO `order_info` VALUES ('2020', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:27', null);
INSERT INTO `order_info` VALUES ('2021', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:27', null);
INSERT INTO `order_info` VALUES ('2022', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:27', null);
INSERT INTO `order_info` VALUES ('2023', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:27', null);
INSERT INTO `order_info` VALUES ('2024', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:27', null);
INSERT INTO `order_info` VALUES ('2025', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:27', null);
INSERT INTO `order_info` VALUES ('2026', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:28', null);
INSERT INTO `order_info` VALUES ('2027', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:28', null);
INSERT INTO `order_info` VALUES ('2028', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:28', null);
INSERT INTO `order_info` VALUES ('2029', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:28', null);
INSERT INTO `order_info` VALUES ('2030', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:28', null);
INSERT INTO `order_info` VALUES ('2031', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:28', null);
INSERT INTO `order_info` VALUES ('2032', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:28', null);
INSERT INTO `order_info` VALUES ('2033', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:28', null);
INSERT INTO `order_info` VALUES ('2034', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:28', null);
INSERT INTO `order_info` VALUES ('2035', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:28', null);
INSERT INTO `order_info` VALUES ('2036', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:28', null);
INSERT INTO `order_info` VALUES ('2037', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:28', null);
INSERT INTO `order_info` VALUES ('2038', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:28', null);
INSERT INTO `order_info` VALUES ('2039', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:14:28', null);
INSERT INTO `order_info` VALUES ('2040', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:12', null);
INSERT INTO `order_info` VALUES ('2041', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:13', null);
INSERT INTO `order_info` VALUES ('2042', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:13', null);
INSERT INTO `order_info` VALUES ('2043', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:13', null);
INSERT INTO `order_info` VALUES ('2044', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:13', null);
INSERT INTO `order_info` VALUES ('2045', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:13', null);
INSERT INTO `order_info` VALUES ('2046', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:13', null);
INSERT INTO `order_info` VALUES ('2047', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:13', null);
INSERT INTO `order_info` VALUES ('2048', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:13', null);
INSERT INTO `order_info` VALUES ('2049', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:13', null);
INSERT INTO `order_info` VALUES ('2050', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:13', null);
INSERT INTO `order_info` VALUES ('2051', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:13', null);
INSERT INTO `order_info` VALUES ('2052', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:13', null);
INSERT INTO `order_info` VALUES ('2053', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:13', null);
INSERT INTO `order_info` VALUES ('2054', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:13', null);
INSERT INTO `order_info` VALUES ('2055', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:13', null);
INSERT INTO `order_info` VALUES ('2056', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:13', null);
INSERT INTO `order_info` VALUES ('2057', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:13', null);
INSERT INTO `order_info` VALUES ('2058', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:13', null);
INSERT INTO `order_info` VALUES ('2059', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:13', null);
INSERT INTO `order_info` VALUES ('2060', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:14', null);
INSERT INTO `order_info` VALUES ('2061', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:14', null);
INSERT INTO `order_info` VALUES ('2062', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:14', null);
INSERT INTO `order_info` VALUES ('2063', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:14', null);
INSERT INTO `order_info` VALUES ('2064', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:14', null);
INSERT INTO `order_info` VALUES ('2065', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:14', null);
INSERT INTO `order_info` VALUES ('2066', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:14', null);
INSERT INTO `order_info` VALUES ('2067', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:14', null);
INSERT INTO `order_info` VALUES ('2068', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:14', null);
INSERT INTO `order_info` VALUES ('2069', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:14', null);
INSERT INTO `order_info` VALUES ('2070', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:14', null);
INSERT INTO `order_info` VALUES ('2071', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:14', null);
INSERT INTO `order_info` VALUES ('2072', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:14', null);
INSERT INTO `order_info` VALUES ('2073', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:14', null);
INSERT INTO `order_info` VALUES ('2074', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:14', null);
INSERT INTO `order_info` VALUES ('2075', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:14', null);
INSERT INTO `order_info` VALUES ('2076', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:14', null);
INSERT INTO `order_info` VALUES ('2077', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:14', null);
INSERT INTO `order_info` VALUES ('2078', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:14', null);
INSERT INTO `order_info` VALUES ('2079', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:14', null);
INSERT INTO `order_info` VALUES ('2080', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:15', null);
INSERT INTO `order_info` VALUES ('2081', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:15', null);
INSERT INTO `order_info` VALUES ('2082', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:15', null);
INSERT INTO `order_info` VALUES ('2083', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:15', null);
INSERT INTO `order_info` VALUES ('2084', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:15', null);
INSERT INTO `order_info` VALUES ('2085', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:15', null);
INSERT INTO `order_info` VALUES ('2086', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:15', null);
INSERT INTO `order_info` VALUES ('2087', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:15', null);
INSERT INTO `order_info` VALUES ('2088', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:15', null);
INSERT INTO `order_info` VALUES ('2089', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:15', null);
INSERT INTO `order_info` VALUES ('2090', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:15', null);
INSERT INTO `order_info` VALUES ('2091', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:15', null);
INSERT INTO `order_info` VALUES ('2092', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:15', null);
INSERT INTO `order_info` VALUES ('2093', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:15', null);
INSERT INTO `order_info` VALUES ('2094', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:15', null);
INSERT INTO `order_info` VALUES ('2095', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:15', null);
INSERT INTO `order_info` VALUES ('2096', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:15', null);
INSERT INTO `order_info` VALUES ('2097', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:15', null);
INSERT INTO `order_info` VALUES ('2098', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:15', null);
INSERT INTO `order_info` VALUES ('2099', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:15', null);
INSERT INTO `order_info` VALUES ('2100', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:15', null);
INSERT INTO `order_info` VALUES ('2101', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:15', null);
INSERT INTO `order_info` VALUES ('2102', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:15', null);
INSERT INTO `order_info` VALUES ('2103', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:15', null);
INSERT INTO `order_info` VALUES ('2104', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:15', null);
INSERT INTO `order_info` VALUES ('2105', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:15', null);
INSERT INTO `order_info` VALUES ('2106', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:15', null);
INSERT INTO `order_info` VALUES ('2107', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:15', null);
INSERT INTO `order_info` VALUES ('2108', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:15', null);
INSERT INTO `order_info` VALUES ('2109', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:15', null);
INSERT INTO `order_info` VALUES ('2110', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:15', null);
INSERT INTO `order_info` VALUES ('2111', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:15', null);
INSERT INTO `order_info` VALUES ('2112', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:15', null);
INSERT INTO `order_info` VALUES ('2113', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:15', null);
INSERT INTO `order_info` VALUES ('2114', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:15', null);
INSERT INTO `order_info` VALUES ('2115', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:16', null);
INSERT INTO `order_info` VALUES ('2116', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:16', null);
INSERT INTO `order_info` VALUES ('2117', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:16', null);
INSERT INTO `order_info` VALUES ('2118', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:16', null);
INSERT INTO `order_info` VALUES ('2119', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:16', null);
INSERT INTO `order_info` VALUES ('2120', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:16', null);
INSERT INTO `order_info` VALUES ('2121', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:16', null);
INSERT INTO `order_info` VALUES ('2122', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:16', null);
INSERT INTO `order_info` VALUES ('2123', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:16', null);
INSERT INTO `order_info` VALUES ('2124', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:16', null);
INSERT INTO `order_info` VALUES ('2125', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:16', null);
INSERT INTO `order_info` VALUES ('2126', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:16', null);
INSERT INTO `order_info` VALUES ('2127', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:16', null);
INSERT INTO `order_info` VALUES ('2128', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:16', null);
INSERT INTO `order_info` VALUES ('2129', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:16', null);
INSERT INTO `order_info` VALUES ('2130', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:16', null);
INSERT INTO `order_info` VALUES ('2131', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:16', null);
INSERT INTO `order_info` VALUES ('2132', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:16', null);
INSERT INTO `order_info` VALUES ('2133', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:16', null);
INSERT INTO `order_info` VALUES ('2134', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:16', null);
INSERT INTO `order_info` VALUES ('2135', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:16', null);
INSERT INTO `order_info` VALUES ('2136', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:16', null);
INSERT INTO `order_info` VALUES ('2137', '1', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:23:16', null);
INSERT INTO `order_info` VALUES ('2138', '10', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:26:42', null);
INSERT INTO `order_info` VALUES ('2139', '10', '3', '妙脆角', '1', '3.00', '-1', '2021-07-23 14:32:12', null);

-- ----------------------------
-- Table structure for `user_info`
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `real_name` varchar(255) DEFAULT NULL,
  `pic_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE KEY `username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES ('1', 'admin', '4297f44b13955235245b2497399d7a93', '管理员', null);
INSERT INTO `user_info` VALUES ('2', 'yy', 'yy', null, null);
INSERT INTO `user_info` VALUES ('3', 'aa', 'aa', null, null);
INSERT INTO `user_info` VALUES ('4', 'bb', 'bb', null, null);
INSERT INTO `user_info` VALUES ('5', 'haha', '123', '哈哈', null);
INSERT INTO `user_info` VALUES ('6', 'qiqi', '123', '琪琪', null);
INSERT INTO `user_info` VALUES ('7', 'weiwie', '123', '微微', null);
INSERT INTO `user_info` VALUES ('8', 'hehe', '123', '呵呵', null);
INSERT INTO `user_info` VALUES ('10', 'izayoirinn', '4297f44b13955235245b2497399d7a93', '超级管理员', null);
