DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_smartgen') THEN

		create table smartgen.customers (
			customer_id bigserial not null,
			fullname varchar(200) null,
			name varchar(100) null,
			surname varchar(200) null,
			constraint pk_customers primary key (customer_id)
		) tablespace tbl_smartgen;
		
		create table smartgen.categories (
			category_id bigserial not null,
			name varchar(100) null,
			constraint pk_categories primary key (category_id)
		) tablespace tbl_smartgen;
		
		create table smartgen.subcategories (
			subcategory_id bigserial not null,
			category_id bigint null,
			name varchar(100) null,
			constraint pk_subcategories primary key (subcategory_id)
		) tablespace tbl_smartgen;
		
		create index idx_subcategories_category_id on
		smartgen.subcategories
			using btree (category_id);
		
		alter table smartgen.subcategories add constraint fk_subcategories_category_id foreign key (category_id) references smartgen.categories(category_id) ON DELETE CASCADE;
		
		create table smartgen.products (
			product_id bigserial not null,
			product_name varchar(100) null,
			category_id bigint null,
			subcategory_id bigint null,
			price float default 0, 
			stock int default 0,
			available bool null,
			constraint pk_products primary key (product_id)
		) tablespace tbl_smartgen;
		
		create index idx_products_category_id on
		smartgen.products
			using btree (category_id);
		
		alter table smartgen.products add constraint fk_products_category_id foreign key (category_id) references smartgen.categories(category_id) ON DELETE CASCADE;
		
		create index idx_products_subcategory_id on
		smartgen.products
			using btree (subcategory_id);
		
		alter table smartgen.products add constraint fk_products_subcategory_id foreign key (subcategory_id) references smartgen.subcategories(subcategory_id) ON DELETE CASCADE;
		
		create table smartgen.orders (
			order_id bigserial not null,
			customer_id bigint not null,
			comments varchar(1000) null,
			created_at timestamptz default current_timestamp not null,
			constraint pk_orders primary key (order_id)
		) tablespace tbl_smartgen;
		
		create index idx_orders_customer_id on
		smartgen.orders
			using btree (customer_id);
		
		alter table smartgen.orders add constraint fk_orders_customer_id foreign key (customer_id) references smartgen.customers(customer_id) ON DELETE CASCADE;
		
		create table smartgen.order_details (
			order_detail_id bigserial not null,
			order_id bigint null,
			product_id bigint null,
			qty int default 0,
			constraint pk_order_details primary key (order_detail_id)
		) tablespace tbl_smartgen;
		
		create index idx_order_details_order_id on
		smartgen.order_details
			using btree (order_id);
		
		create index idx_order_details_product_id on
		smartgen.order_details
			using btree (product_id);
		
		alter table smartgen.order_details add constraint fk_order_details_order_id foreign key (order_id) references smartgen.orders(order_id)  ON DELETE CASCADE;
		
		alter table smartgen.order_details add constraint fk_order_details_product_id foreign key (product_id) references smartgen.products(product_id)  ON DELETE CASCADE;
  END IF;
END $$;