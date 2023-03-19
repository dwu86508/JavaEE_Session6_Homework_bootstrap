DROP TABLE BEVERAGE_ORDER;
DROP TABLE BEVERAGE_MEMBER;
DROP TABLE BEVERAGE_GOODS;

-- 會員資料表
CREATE TABLE BEVERAGE_MEMBER
(
  IDENTIFICATION_NO VARCHAR2(10) NOT NULL 
, PASSWORD VARCHAR2(50) 
, CUSTOMER_NAME VARCHAR2(50) 
, CONSTRAINT BEVERAGE_MEMBER_PK PRIMARY KEY (IDENTIFICATION_NO)  
);

COMMENT ON COLUMN BEVERAGE_MEMBER.IDENTIFICATION_NO IS '身份證字號';
COMMENT ON COLUMN BEVERAGE_MEMBER.PASSWORD IS '密碼';
COMMENT ON COLUMN BEVERAGE_MEMBER.CUSTOMER_NAME IS '顧客姓名';

INSERT INTO BEVERAGE_MEMBER (IDENTIFICATION_NO,PASSWORD,CUSTOMER_NAME) VALUES ('A124243295','123','Jay');
INSERT INTO BEVERAGE_MEMBER (IDENTIFICATION_NO,PASSWORD,CUSTOMER_NAME) VALUES ('D201663865','123','Tiffany');
INSERT INTO BEVERAGE_MEMBER (IDENTIFICATION_NO,PASSWORD,CUSTOMER_NAME) VALUES ('J213664153','123','Jolin');
INSERT INTO BEVERAGE_MEMBER (IDENTIFICATION_NO,PASSWORD,CUSTOMER_NAME) VALUES ('F126873254','123','Rita');
INSERT INTO BEVERAGE_MEMBER (IDENTIFICATION_NO,PASSWORD,CUSTOMER_NAME) VALUES ('G436565447','123','Wendy');

-- 商品資料表
CREATE TABLE BEVERAGE_GOODS
(
  GOODS_ID NUMBER NOT NULL
, GOODS_NAME VARCHAR2(100) NOT NULL 
, DESCRIPTION VARCHAR2(300)
, PRICE NUMBER NOT NULL 
, QUANTITY NUMBER NOT NULL 
, IMAGE_NAME VARCHAR2(300) NOT NULL 
, STATUS CHAR(1) NOT NULL CHECK (STATUS IN('1','0')) 
, CONSTRAINT BEVERAGE_GOODS_PK PRIMARY KEY(GOODS_ID)
);

COMMENT ON COLUMN BEVERAGE_GOODS.GOODS_ID IS '商品編號';
COMMENT ON COLUMN BEVERAGE_GOODS.GOODS_NAME IS '商品名稱';
COMMENT ON COLUMN BEVERAGE_GOODS.DESCRIPTION IS '商品描述';
COMMENT ON COLUMN BEVERAGE_GOODS.PRICE IS '商品價格';
COMMENT ON COLUMN BEVERAGE_GOODS.QUANTITY IS '商品庫存量';
COMMENT ON COLUMN BEVERAGE_GOODS.IMAGE_NAME IS '商品圖片名稱';
COMMENT ON COLUMN BEVERAGE_GOODS.STATUS IS '商品狀態(1:上架、0:下架)';

DROP SEQUENCE BEVERAGE_GOODS_SEQ;
CREATE SEQUENCE BEVERAGE_GOODS_SEQ
INCREMENT BY 1
START WITH 1
NOMAXVALUE
NOCYCLE
NOCACHE;

Insert into BEVERAGE_GOODS (GOODS_ID,GOODS_NAME,DESCRIPTION,PRICE,QUANTITY,IMAGE_NAME,STATUS) values (BEVERAGE_GOODS_SEQ.NEXTVAL,'金桔百香鮮果茶','內含清香金桔、酸甜百香果，與淡雅甘甜的茉莉綠茶，交織出迷人芳香、酸中帶甜的美好滋味。',100,5,'2020040717580083.jpg','1');
Insert into BEVERAGE_GOODS (GOODS_ID,GOODS_NAME,DESCRIPTION,PRICE,QUANTITY,IMAGE_NAME,STATUS) values (BEVERAGE_GOODS_SEQ.NEXTVAL,'紅心芭樂檸檬鮮果茶','內含香氣濃郁有「紅鑽石」之稱的紅心芭樂，搭配具有豐富膳食纖維的紅龍果，結合高維生素C的清香檸檬。',7,7,'20200407180213118.jpg','1');
Insert into BEVERAGE_GOODS (GOODS_ID,GOODS_NAME,DESCRIPTION,PRICE,QUANTITY,IMAGE_NAME,STATUS) values (BEVERAGE_GOODS_SEQ.NEXTVAL,'柳橙檸檬蜂蜜水','廣受喜愛的蜂蜜水，搭配柳橙與檸檬汁，酸甜的好滋味，尾韻更帶有柑橘清香。',20,5,'2021110210202761.jpg','1');
Insert into BEVERAGE_GOODS (GOODS_ID,GOODS_NAME,DESCRIPTION,PRICE,QUANTITY,IMAGE_NAME,STATUS) values (BEVERAGE_GOODS_SEQ.NEXTVAL,'柳橙汁','天然柳橙原汁，酸甜口感，清新爽口。總重量：300毫升',10,11,'20190201101804603.jpg','1');
Insert into BEVERAGE_GOODS (GOODS_ID,GOODS_NAME,DESCRIPTION,PRICE,QUANTITY,IMAGE_NAME,STATUS) values (BEVERAGE_GOODS_SEQ.NEXTVAL,'葡萄莓果綜合果汁','特選葡萄、草莓、藍莓、蔓越莓等水果調配而成，是一款健康且味道豐富的果汁。',25,14,'20190201104121279.jpg','1');
Insert into BEVERAGE_GOODS (GOODS_ID,GOODS_NAME,DESCRIPTION,PRICE,QUANTITY,IMAGE_NAME,STATUS) values (BEVERAGE_GOODS_SEQ.NEXTVAL,'藍莓優格飲','用喝的優格! 優格加水果搖一搖風味獨特，飲用更方便。嚴選北美野生小藍莓製成的果漿再搭配馬修嚴選原味優格。',25,13,'20180131121732511.jpg','1');
Insert into BEVERAGE_GOODS (GOODS_ID,GOODS_NAME,DESCRIPTION,PRICE,QUANTITY,IMAGE_NAME,STATUS) values (BEVERAGE_GOODS_SEQ.NEXTVAL,'Cascara Macchiato','融合新鮮蒸奶及香草風味糖漿後，倒入濃縮咖啡並在奶泡上覆以香甜焦糖醬，呈現多層次風味，是星巴克深受歡迎的飲料。',140,20,'20190124150636383.jpg','1');
Insert into BEVERAGE_GOODS (GOODS_ID,GOODS_NAME,DESCRIPTION,PRICE,QUANTITY,IMAGE_NAME,STATUS) values (BEVERAGE_GOODS_SEQ.NEXTVAL,'Iced Cascara','由濃縮咖啡、摩卡醬及新鮮蒸奶調製，覆上輕盈柔細的鮮奶油，帶來香濃的巧克力及咖啡風味。',140,20,'20190124151236425.jpg','1');
Insert into BEVERAGE_GOODS (GOODS_ID,GOODS_NAME,DESCRIPTION,PRICE,QUANTITY,IMAGE_NAME,STATUS) values (BEVERAGE_GOODS_SEQ.NEXTVAL,'Dark Caramel Latte','融合濃縮咖啡及現蒸牛奶，加上豐厚細緻的奶泡，呈現醇厚咖啡風味。',140,20,'20190314152120770.jpg','1');
Insert into BEVERAGE_GOODS (GOODS_ID,GOODS_NAME,DESCRIPTION,PRICE,QUANTITY,IMAGE_NAME,STATUS) values (BEVERAGE_GOODS_SEQ.NEXTVAL,'White Chocolate Mocha','第一口綿密香醇的奶泡混合著香甜可可的糖醬滋味，搭配經典義式濃縮咖啡，層次堆疊滿足味蕾的享受。',135,20,'20190315112127432.jpg','1');
Insert into BEVERAGE_GOODS (GOODS_ID,GOODS_NAME,DESCRIPTION,PRICE,QUANTITY,IMAGE_NAME,STATUS) values (BEVERAGE_GOODS_SEQ.NEXTVAL,'Flat White','創新的現煮義式飲品!滿足咖啡饕客的咖啡味蕾。綿滑香柔的雲朵(冰奶泡)是由低脂牛奶打製而成。',135,20,'20180910173353352.jpg','1');
Insert into BEVERAGE_GOODS (GOODS_ID,GOODS_NAME,DESCRIPTION,PRICE,QUANTITY,IMAGE_NAME,STATUS) values (BEVERAGE_GOODS_SEQ.NEXTVAL,'Choice Extra Shot Latte','濃郁醇厚的濃縮咖啡，搭配新鮮蒸煮的優質鮮奶，覆上綿密細緻的奶泡',145,20,'20170717104522150.jpg','1');
Insert into BEVERAGE_GOODS (GOODS_ID,GOODS_NAME,DESCRIPTION,PRICE,QUANTITY,IMAGE_NAME,STATUS) values (BEVERAGE_GOODS_SEQ.NEXTVAL,'Cappuccino','使用精心臻選，品質優良的日本純抹茶，精緻研磨成無糖醇濃抹茶粉，與義式濃縮咖啡結合',120,20,'20170627162058676.jpg','1');
Insert into BEVERAGE_GOODS (GOODS_ID,GOODS_NAME,DESCRIPTION,PRICE,QUANTITY,IMAGE_NAME,STATUS) values (BEVERAGE_GOODS_SEQ.NEXTVAL,'Caffe Latte','綿密香醇的奶泡混合著香甜可可糖醬滋味，搭配經典義式濃縮咖啡與口感醇厚的植物奶，層次堆疊滿足味蕾的享受。',110,20,'2013081316151831.jpg','1');
Insert into BEVERAGE_GOODS (GOODS_ID,GOODS_NAME,DESCRIPTION,PRICE,QUANTITY,IMAGE_NAME,STATUS) values (BEVERAGE_GOODS_SEQ.NEXTVAL,'Iced Caffe Latte','以秋季經典糕點南瓜派作為風味設計來源，將經典義式濃縮咖啡與牛奶混合，添加南瓜、肉桂、荳蔻、丁香等香氣',120,20,'20130813162740315.jpg','1');
Insert into BEVERAGE_GOODS (GOODS_ID,GOODS_NAME,DESCRIPTION,PRICE,QUANTITY,IMAGE_NAME,STATUS) values (BEVERAGE_GOODS_SEQ.NEXTVAL,'Caramel Macchiato','源自日本，以烤焙過的日本焙茶，與新鮮蒸煮的牛奶結合，覆上細緻綿密的奶泡，濃郁滑順的口感中',140,20,'20170627171958121.jpg','1');
Insert into BEVERAGE_GOODS (GOODS_ID,GOODS_NAME,DESCRIPTION,PRICE,QUANTITY,IMAGE_NAME,STATUS) values (BEVERAGE_GOODS_SEQ.NEXTVAL,'Iced Caramel Macchiato','使用精心臻選，品質優良的日本純抹茶，精緻研磨成無糖醇濃抹茶粉，加入新鮮牛奶與原味糖漿製成',140,20,'20170627175758131.jpg','1');
Insert into BEVERAGE_GOODS (GOODS_ID,GOODS_NAME,DESCRIPTION,PRICE,QUANTITY,IMAGE_NAME,STATUS) values (BEVERAGE_GOODS_SEQ.NEXTVAL,'Caffe Mocha','帶著佛手柑香料風味的伯爵紅茶，搭配淡淡優雅的薰衣草香氣，與香草風味糖漿和奶香結合',135,20,'20130813161442705.jpg','1');
Insert into BEVERAGE_GOODS (GOODS_ID,GOODS_NAME,DESCRIPTION,PRICE,QUANTITY,IMAGE_NAME,STATUS) values (BEVERAGE_GOODS_SEQ.NEXTVAL,'Iced Caffe Mocha','使用阿薩姆紅茶與現蒸鮮奶完美融合，覆上濃郁奶泡，帶來平衡甜美的絕妙口感。',135,20,'20130813163137244.jpg','1');
Insert into BEVERAGE_GOODS (GOODS_ID,GOODS_NAME,DESCRIPTION,PRICE,QUANTITY,IMAGE_NAME,STATUS) values (BEVERAGE_GOODS_SEQ.NEXTVAL,'Caffe Americano','使用玫瑰蜜香紅茶與現蒸牛奶，加上濃郁奶泡，細緻茶香好是宜人。',80,20,'20130813161023652.jpg','1');
Insert into BEVERAGE_GOODS (GOODS_ID,GOODS_NAME,DESCRIPTION,PRICE,QUANTITY,IMAGE_NAME,STATUS) values (BEVERAGE_GOODS_SEQ.NEXTVAL,'Iced Caffe Americano','經典的蜜柚紅茶水果茶系列再增添新的成員，將傳統的西方紅茶體驗，加入創新元素。',95,20,'20130813163246257.jpg','1');
Insert into BEVERAGE_GOODS (GOODS_ID,GOODS_NAME,DESCRIPTION,PRICE,QUANTITY,IMAGE_NAME,STATUS) values (BEVERAGE_GOODS_SEQ.NEXTVAL,'Espresso','讓人心情和緩放鬆的洋甘菊花草茶，蘋果般的甜美香味中隱藏芬芳的檸檬草、玫瑰花瓣、薄荷、芙蓉花。',80,20,'20130813160909561.jpg','1');
Insert into BEVERAGE_GOODS (GOODS_ID,GOODS_NAME,DESCRIPTION,PRICE,QUANTITY,IMAGE_NAME,STATUS) values (BEVERAGE_GOODS_SEQ.NEXTVAL,'Triple Mocha','將傳統的西方紅茶體驗，加入創新元素，轉換成帶有新意又美味，同時適合熱飲及冰飲的茶品。',145,20,'20190314152226279.jpg','1');
Insert into BEVERAGE_GOODS (GOODS_ID,GOODS_NAME,DESCRIPTION,PRICE,QUANTITY,IMAGE_NAME,STATUS) values (BEVERAGE_GOODS_SEQ.NEXTVAL,'Dark Caramel Coffee','使用優質果茶製作，搭配清爽的檸檬風味果汁，嘗來富迷人果香、清新舒暢。',145,20,'20190314152210263.jpg','1');
Insert into BEVERAGE_GOODS (GOODS_ID,GOODS_NAME,DESCRIPTION,PRICE,QUANTITY,IMAGE_NAME,STATUS) values (BEVERAGE_GOODS_SEQ.NEXTVAL,'Coffee','這款經典的星冰樂是為了讓咖啡愛好者在夏天也能盡情享用咖啡而發明的。綿密口感及獨特咖啡香氣。',105,20,'20130813155657355.jpg','1');
Insert into BEVERAGE_GOODS (GOODS_ID,GOODS_NAME,DESCRIPTION,PRICE,QUANTITY,IMAGE_NAME,STATUS) values (BEVERAGE_GOODS_SEQ.NEXTVAL,'Caramel Frappuccino','以星巴克咖啡、乳品及焦糖風味糖漿調製而成，搭配鮮奶油及甜美焦糖醬，帶來冰涼宜人的享受。',130,20,'20130813155636918.jpg','1');
Insert into BEVERAGE_GOODS (GOODS_ID,GOODS_NAME,DESCRIPTION,PRICE,QUANTITY,IMAGE_NAME,STATUS) values (BEVERAGE_GOODS_SEQ.NEXTVAL,'Caramel Java Chip','甜蜜芬芳的哈密瓜與醇香的白巧克力奶霜星冰樂結合成的誘人風味，上面佐以淋上哈密瓜果醬的鮮奶油。',145,20,'20140304171733401.jpg','1');
Insert into BEVERAGE_GOODS (GOODS_ID,GOODS_NAME,DESCRIPTION,PRICE,QUANTITY,IMAGE_NAME,STATUS) values (BEVERAGE_GOODS_SEQ.NEXTVAL,'Java Chip','暢銷口味之一，以摩卡醬、乳品及可可碎片調製，加上細緻鮮奶油及摩卡醬，濃厚的巧克力風味。',145,20,'20130813154445805.jpg','1');

-- 訂單資料表
CREATE TABLE BEVERAGE_ORDER 
(
  ORDER_ID NUMBER NOT NULL
, ORDER_DATE DATE NOT NULL
, CUSTOMER_ID VARCHAR2(10) NOT NULL 
, GOODS_ID NUMBER NOT NULL
, GOODS_BUY_PRICE NUMBER NOT NULL 
, BUY_QUANTITY NUMBER NOT NULL 
, CONSTRAINT BEVERAGE_ORDER_PK PRIMARY KEY (ORDER_ID)
, CONSTRAINT BEVERAGE_MEMBER_FK FOREIGN KEY (CUSTOMER_ID) REFERENCES BEVERAGE_MEMBER (IDENTIFICATION_NO)
, CONSTRAINT BEVERAGE_GOODS_FK FOREIGN KEY (GOODS_ID) REFERENCES BEVERAGE_GOODS (GOODS_ID)
);

COMMENT ON COLUMN BEVERAGE_ORDER.ORDER_ID IS '訂單編號';
COMMENT ON COLUMN BEVERAGE_ORDER.ORDER_DATE IS '訂單日期';
COMMENT ON COLUMN BEVERAGE_ORDER.CUSTOMER_ID IS '顧客編號(BEVERAGE_MEMBER.IDENTIFICATION_NO)';
COMMENT ON COLUMN BEVERAGE_ORDER.GOODS_ID IS '商品編號(BEVERAGE_GOODS.GOODS_ID)';
COMMENT ON COLUMN BEVERAGE_ORDER.GOODS_BUY_PRICE IS '商品金額(購買單價)';
COMMENT ON COLUMN BEVERAGE_ORDER.BUY_QUANTITY IS '購買數量';

DROP SEQUENCE BEVERAGE_ORDER_SEQ;
CREATE SEQUENCE BEVERAGE_ORDER_SEQ
INCREMENT BY 1
START WITH 1
NOMAXVALUE
NOCYCLE
NOCACHE;

COMMIT;