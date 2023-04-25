ALTER TABLE tickets ADD COLUMN IF NOT EXISTS customer_id int8 NULL;
ALTER TABLE public.tickets ADD CONSTRAINT fkhkvbxmpymlc2thjipyqiabjxd FOREIGN KEY (customer_id) REFERENCES public.customer(id);
