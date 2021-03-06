if object_id('TestParameter')is not null drop table TestParameter
if object_id('TestComponent')is not null drop table TestComponent
if object_id('Test')is not null drop table Test
if object_id('TestCategory')is not null drop table TestCategory

if object_id('ComponentScript')is not null drop table ComponentScript
if object_id('ComponentParameter')is not null drop table ComponentParameter
if object_id('ParameterContext')is not null drop table ParameterContext
if object_id('Component')is not null drop table Component
if object_id('ComponentCategory')is not null drop table ComponentCategory

if object_id('ScriptParameter')is not null drop table ScriptParameter
if object_id('Script')is not null drop table Script
if object_id('ScriptParameterDirection')is not null drop table ScriptParameterDirection
if object_id('ScriptCategory')is not null drop table ScriptCategory
if object_id('ScriptParameterDirection')is not null drop table ScriptParameterDirection

go
if object_id('GetTestCategories')is not null drop procedure GetTestCategories
if object_id('GetScriptCategories')is not null drop procedure GetScriptCategories
if object_id('GetComponentCategories')is not null drop procedure GetComponentCategories
if object_id('DeleteTest')is not null drop procedure DeleteTest
if object_id('DeleteScript')is not null drop procedure DeleteScript
if object_id('DeleteComponent')is not null drop procedure DeleteComponent
go

SET ANSI_NULLS, QUOTED_IDENTIFIER, ANSI_PADDING ON
GO



CREATE TABLE ScriptParameterDirection
(
	Id INT NOT NULL PRIMARY KEY,
	Name VARCHAR(20) NOT NULL
)

CREATE TABLE TestCategory
(
	Id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
	Name VARCHAR(50) NOT NULL,
	parentId INT CONSTRAINT fk_TestCategory_parent FOREIGN KEY REFERENCES TestCategory (Id)
)

CREATE TABLE ScriptCategory
(
	Id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
	Name VARCHAR(50) NOT NULL,
	parentId INT NULL CONSTRAINT fk_ScriptCategory_parent FOREIGN KEY REFERENCES ScriptCategory (Id)
)

CREATE INDEX idx_ScriptCategory_parent ON ScriptCategory(parentId)

CREATE TABLE ParameterContext
(
	Id INT NOT NULL PRIMARY KEY,
	Name VARCHAR(50) NOT NULL,
	comment VARCHAR(255) NULL
)

CREATE TABLE ComponentCategory
(
	Id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
	Name VARCHAR(50) NOT NULL,
	parentId INT constraINT fk_ComponentCategory_parent foreign key references ComponentCategory (Id)
)

CREATE TABLE Component
(
	Id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
	Name VARCHAR(50) NOT NULL,
	categoryId INT NULL constraINT fk_Component_Categoryid foreign key references ComponentCategory (Id),
	comment VARCHAR(500) NULL
) 

CREATE INDEX idx_Component_Category ON Component(categoryId)


CREATE TABLE Script
(
	Id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
	Name VARCHAR(50) NOT NULL,
	categoryId INT NULL CONSTRAINT fk_Script_Categoryid FOREIGN KEY REFERENCES ScriptCategory (Id),
	comment VARCHAR(500) NULL,
	[text] VARCHAR(MAX) NULL,
	[type] TINYINT NOT NULL CONSTRAINT df_Script_type  DEFAULT (0),
	StateParam VARCHAR(50) NULL,
	StateParamValue VARCHAR(MAX) NULL
)

CREATE INDEX idx_Script_category ON Script(categoryId)

CREATE TABLE Test
(
	Id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
	Name VARCHAR(50) NOT NULL,
	categoryId INT NULL CONSTRAINT fk_Test_Categoryid FOREIGN KEY REFERENCES TestCategory (Id),
	comment VARCHAR(500) NULL
)

CREATE TABLE TestParameter
(
	Id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
	testId INT NOT NULL CONSTRAINT fk_TestParameter_test FOREIGN KEY REFERENCES Test (Id),
	Name VARCHAR(50) NOT NULL,
	contextId INT NOT NULL CONSTRAINT fk_TestParameter_ParameterContext FOREIGN KEY REFERENCES ParameterContext (Id)
)

CREATE INDEX idx_TestParameter_testid ON TestParameter(testId)

CREATE TABLE TestComponent
(
	testId INT NOT NULL CONSTRAINT fk_TestComponent_test FOREIGN KEY REFERENCES Test(Id),
	componentId INT NOT NULL CONSTRAINT fk_TestComponent_component FOREIGN KEY REFERENCES Component(Id),
	orderid INT NOT NULL,
	mappedParams VARCHAR(MAX) NULL
)
GO

CREATE TABLE ScriptParameter
(
	Id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
	ScriptId INT NOT NULL CONSTRAINT fk_ScriptParameter_Script FOREIGN KEY REFERENCES Script (Id),
	DirectionId INT NOT NULL CONSTRAINT fk_ScriptParameter_Direction FOREIGN KEY REFERENCES ScriptParameterDirection (Id),
	Name VARCHAR(50) NOT NULL,
	ParamType VARCHAR(50) NULL,
	ParamSize VARCHAR(10) NULL
)
CREATE INDEX idx_ScriptParameter_script ON ScriptParameter(ScriptId)
CREATE UNIQUE INDEX ux_ScriptParameter_name ON ScriptParameter(ScriptId, Name)

CREATE TABLE ComponentScript
(
	componentId INT NOT NULL CONSTRAINT fk_ComponentScript_component FOREIGN KEY REFERENCES Component(Id),
	ScriptId INT NOT NULL CONSTRAINT fk_ComponentScript_script FOREIGN KEY REFERENCES Script(Id),
	orderid INT NOT NULL,
	mappedParams VARCHAR(MAX) NULL
)

CREATE INDEX idx_ComponentScript_component ON ComponentScript(componentId)

CREATE TABLE ComponentParameter
(
	Id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
	componentId INT NOT NULL CONSTRAINT fk_ComponentParameter_Component FOREIGN KEY REFERENCES Component (Id),
	Name VARCHAR(50) NOT NULL,
	contextId INT NOT NULL CONSTRAINT fk_ComponentParameter_ParameterContext FOREIGN KEY REFERENCES ParameterContext (Id)
)

CREATE INDEX idx_ComponentParameter_componentid ON ComponentParameter(componentId)
go

go
create procedure GetTestCategories
	@id INT
as
begin
set nocount on
declare @childs table(id INT not null PRIMARY KEY, level INT)
declare @childs2 table(id INT not null PRIMARY KEY, level INT)
declare @childs3 table(id INT not null PRIMARY KEY, level INT)
			
declare @rows INT, @level INT = 0
insert INTo @childs(id, level) values (@id, @level)
while 1 = 1
begin
	set @level += 1
	/*read childs*/
	insert INTo @childs2(id, level)
	select sc.id, @level
	from TestCategory sc
	inner join @childs c
		on c.id = sc.parentId
	
	select @rows = @@ROWCOUNT
	/*save old ID's*/
	insert INTo @childs3(id, level)
	select id, level
	from @childs
	
	delete from @childs
	insert INTo @childs(id, level)
	select id, level
	from @childs2
	delete from @childs2
	
	if @rows = 0
		break
end
select id, level
from @childs3 c
order by level desc
end
GO

create procedure GetScriptCategories
	@id INT
as
begin
set nocount on
declare @childs table(id INT not null PRIMARY KEY, level INT)
declare @childs2 table(id INT not null PRIMARY KEY, level INT)
declare @childs3 table(id INT not null PRIMARY KEY, level INT)
			
declare @rows INT, @level INT = 0
insert INTo @childs(id, level) values (@id, @level)
while 1 = 1
begin
	set @level += 1
	/*read childs*/
	insert INTo @childs2(id, level)
	select sc.id, @level
	from ScriptCategory sc
	inner join @childs c
		on c.id = sc.parentId
	
	select @rows = @@ROWCOUNT
	/*save old ID's*/
	insert INTo @childs3(id, level)
	select id, level
	from @childs
	
	delete from @childs
	insert INTo @childs(id, level)
	select id, level
	from @childs2
	delete from @childs2
	
	if @rows = 0
		break
end
select id, level
from @childs3 c
order by level desc
end
GO

create procedure GetComponentCategories
	@id INT
as
begin
set nocount on
declare @childs table(id INT not null PRIMARY KEY, level INT)
declare @childs2 table(id INT not null PRIMARY KEY, level INT)
declare @childs3 table(id INT not null PRIMARY KEY, level INT)
			
declare @rows INT, @level INT = 0
insert INTo @childs(id, level) values (@id, @level)
while 1 = 1
begin
	set @level += 1
	/*read childs*/
	insert INTo @childs2(id, level)
	select sc.id, @level
	from ComponentCategory sc
	inner join @childs c
		on c.id = sc.parentId
	
	select @rows = @@ROWCOUNT
	/*save old ID's*/
	insert INTo @childs3(id, level)
	select id, level
	from @childs
	
	delete from @childs
	insert INTo @childs(id, level)
	select id, level
	from @childs2
	delete from @childs2
	
	if @rows = 0
		break
end
select id, level
from @childs3 c
order by level desc
end
GO

create procedure DeleteTest
	@id INT
as
begin
delete from TestParameter where testId = @id
delete from TestComponent where testId = @id
delete from Test where id = @id
end
GO

create procedure DeleteScript
	@id INT
as
begin
delete from ScriptParameter where ScriptId = @id
delete from Script where id = @id
end
GO

create procedure DeleteComponent
	@id INT
as
begin
delete from ComponentParameter where componentId = @id
delete from ComponentScript where componentId = @id
delete from Component where id = @id
end
GO

insert into ScriptParameterDirection(Id, Name)
values (0, 'In'),
		(1, 'InOut'),
		(2, 'Out')

insert into ParameterContext(Id, Name, comment)
values	(0, 'GLOBAL', 'Глобальная переменная'),
		(1, 'LOCAL', 'Локальная переменная'),
		(2, 'RUNTIME', 'Значение времени выполнения, тестовое значение'),
		(3, 'ETALON', 'Эталонное значение'),
		(4, 'CONSTANT', 'Константа')
