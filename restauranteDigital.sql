CREATE DATABASE restaurante;
USE restaurante;

CREATE TABLE tipoUsuario(
    id INT AUTO_INCREMENT,
    nombre VARCHAR (50),

    PRIMARY KEY (id),
    UNIQUE (nombre)
);

CREATE TABLE mesa(
    id INT AUTO_INCREMENT,
    PRIMARY KEY (id)
);

CREATE TABLE categoria (
    id INT AUTO_INCREMENT,
    nombre VARCHAR(100),

    PRIMARY KEY (id),
    UNIQUE(nombre)
);

CREATE TABLE usuario(
    id INT AUTO_INCREMENT,
    tipoUsuario_id_fk INT,
    rut VARCHAR (13),
    nombre VARCHAR (30),
    apellido VARCHAR (30),
    correo VARCHAR(50),
    pass VARCHAR(200),

    PRIMARY KEY (id),
    UNIQUE (rut),
    UNIQUE(correo),
    FOREIGN KEY (tipoUsuario_id_fk) REFERENCES tipoUsuario(id)
);


CREATE TABLE producto (
    id INT AUTO_INCREMENT,
    nombre VARCHAR(100),
    subNombre Varchar(500),
    categoria_id_fk INT,
    stock INT,
    precio INT,

    PRIMARY KEY (id),
    FOREIGN KEY (categoria_id_fk) REFERENCES categoria(id)
);

CREATE TABLE registroHistorico(
    id INT AUTO_INCREMENT,
    producto_id_fk INT,
    nombre VARCHAR(100),
    subNombre VARCHAR(500),
    precio INT,
    stock INT,
    fecha DATETIME,

    PRIMARY KEY (id),
    FOREIGN KEY (producto_id_fk) REFERENCES producto(id)

);

CREATE TABLE factura(
    id INT AUTO_INCREMENT,
    usuario_id_fk INT,
    fecha DATETIME,
    mesa_id_fk INT,
    pagado BIT DEFAULT 0,

    PRIMARY KEY (id),
    FOREIGN KEY (mesa_id_fk) REFERENCES mesa(id),
    FOREIGN KEY (usuario_id_fk) REFERENCES usuario(id)
);

CREATE TABLE detalle(
    id INT AUTO_INCREMENT,
    factura_id_fk INT,
    producto_id_fk INT,
    cantidad INT,
    precio INT,

    PRIMARY KEY (id),
    FOREIGN KEY (factura_id_fk) REFERENCES factura(id),
    FOREIGN KEY (producto_id_fk) REFERENCES producto(id)
);

INSERT INTO mesa VALUES(NULL),(NULL),(NULL),(NULL),(NULL),(NULL),(NULL),(NULL),(NULL),(NULL),(NULL),(NULL),(NULL),(NULL),(NULL),
                       (NULL),(NULL),(NULL),(NULL),(NULL),(NULL),(NULL),(NULL),(NULL),(NULL);


INSERT INTO tipoUsuario VALUES (NULL,'administrador'),(NULL,'usuario');


INSERT INTO usuario VALUES (NULL,1,'18650749-5','Reynaldo Joaquin','Cuadra Acevedo','reynaldo.-@outlook.com',SHA2('1234',0));
INSERT INTO usuario VALUES (NULL,2,'11111111-1','Martin Andres','Soto Tapia','martin.soto@outlook.com',SHA2('12345',0));


INSERT INTO categoria VALUES(NULL,"bebida"),(NULL,"comida"),(NULL,"postre");

iNSERT INTO producto VALUES (NULL,"Coca-Cola",'500 ml',1,150,1500),
                            (NULL,'Coca-Cola Zero','500 ml',1,150,1500),
                            (NULL,'Fanta','500 ml',1,100,1500),
                            (NULL,'Fanta Zero','500 ml',1,100,1500),
                            (NULL,'Sprite','500 ml',1,100,1500),
                            (NULL,'Sprite Zero','500 ml',1,100,1500),
                            (NULL,'Jugo natural frambuesa','500 ml',1,50,2000),
                            (NULL,'Jugo natural melon','500 ml',1,50,2000),
                            (NULL,'Jugo natural piña','500 ml',1,50,2000);

iNSERT INTO producto VALUES (NULL,"LOMO MANTARO",'Una version distinta pero igual de deliciosa acompañada con fetuccinnis en salsa hungara.',2,30,8990),
                            (NULL,'LOMO GIUSEPPI VERDI','Lomo acompañado de deliciosos fetuccinnis bañados en una clasica salsa italiana. ',2,30,7990),
                            (NULL,'BIFE BRAVO','Bife ancho de res acompañado de nuestras    inigualables papas bravas.',2,30,6990),
                            (NULL,'BIFE CORLEONE','Bife ancho de res, bañado en nuestra cremosa salsa de pimienta negra y fetuccinnis bañados en mantequilla y salvia.',2,30,9990),
                            (NULL,'PECHUGA PARRILLERA','Pechuga acompañada con arroz y ensalada mixta.',2,30,7990),
                            (NULL,'POLLO A LA BRASA','1/4 de pollo acompañado con papas fritas.',2,30,6990),
                            (NULL,'PARRILLA PERSONAL','Nuestra deliciosa parrilla incluye bife ancho de res, pechuga de pollo, carne de cerdo, salchicha farnkfuter y papas fritas.',2,30,8990);

iNSERT INTO producto VALUES (NULL,"MOUSSE DE CHOCOLATE",'Bizcocho de chocolate almendrado con mousse de chocolate sobre un espejo de Bailey´s.',3,30,3990),
                            (NULL,'PASTEL OPERA','Una base de biscuit, hojuelas crujientes y un mousse de chocolate amargo.',3,30,3990),
                            (NULL,'WAFFLES CON FRUTOS ROJOS','Waffles belgas con frutilla, mora y frambuesa decorados con crema batida y granillos de     chocolate.',3,30,3990),
                            (NULL,'BIZCOCHO DE PISTACHO','Esponjoso bizcocho de pistacho cubierto con crema de chocolate blanco.',3,30,3990),
                            (NULL,'BANANA SPLIT','Helado de fresa,chocolate, vainilla con nata montada en platanos naturales.',3,30,4990),
                            (NULL,'MACEDONIA','Helado de frambuesa, limon y frutilla sumergidas en frutas en almibar y nata.',3,30,4990),
                            (NULL,'COPA LIMON Y MENTA','Limon granizado, ron de caña y menta.',3,30,4990);                            





DELIMITER //
CREATE PROCEDURE registroVen(IN b INT,IN c INT, IN d INT, IN e INT)
BEGIN
INSERT INTO detalle VALUES(NULL,b,c,d,e);
UPDATE producto SET stock = stock - d WHERE id = c;
END
//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE ingresoInventario(IN a INT,IN b INT)
BEGIN
UPDATE producto SET stock = stock + a WHERE id = b;
END
//
DELIMITER ;



DELIMITER //
CREATE TRIGGER historial_producto BEFORE UPDATE ON producto
    FOR EACH ROW
BEGIN
	INSERT INTO registroHistorico VALUES(NULL,OLD.id,OLD.nombre,OLD.subNombre,OLD.precio,OLD.stock,NOW());
END //
DELIMITER ;





-- PRUEBAS
-- SELECT * FROM producto WHERE nombre = 'Fanta' AND subNombre = '500 ml'AND precio = 1500;


-- SELECT detalle.cantidad, producto.nombre, producto.precio,detalle.precio
-- FROM detalle 
-- INNER JOIN producto ON producto.id = detalle.producto_id_fk
-- WHERE detalle.factura_id_fk = (SELECT MAX(factura.id) FROM usuario INNER JOIN factura ON factura.usuario_id_fk = usuario.id WHERE usuario.rut = '11111111-1');

 -- CALL registroVen((SELECT MAX(factura.id) FROM usuario INNER JOIN factura ON factura.usuario_id_fk = usuario.id WHERE usuario.rut = '11111111-1'),1,2,1500);


-- SELECT MAX(factura.id) 
-- FROM usuario
-- INNER JOIN factura ON factura.usuario_id_fk = usuario.id
-- WHERE usuario.rut = '11111111-1';

-- registroVen((SELECT MAX(factura.id) FROM usuario INNER JOIN factura ON factura.usuario_id_fk = usuario.id WHERE usuario.rut = '11111111-1'),1,5,1500);
 
-- SELECT * FROM usuario WHERE rut = '18650749-5' && pass = SHA2('1234',0);