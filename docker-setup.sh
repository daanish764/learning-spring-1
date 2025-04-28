#!/bin/bash

# Step 1: Create a custom network (if it doesn't already exist)
docker network inspect mynetwork >/dev/null 2>&1 || docker network create mynetwork

# Step 2: Run Postgres container
docker run -d \
  --name mypostgres \
  --network mynetwork \
  -e POSTGRES_PASSWORD=admin \
  -v postgres_data:/var/lib/postgresql/data \
  -p 5432:5432 \
  postgres:latest

# Step 3: Run pgAdmin4 container
docker run -d \
  --name mypgadmin \
  --network mynetwork \
  -e PGADMIN_DEFAULT_EMAIL=admin@gmail.com \
  -e PGADMIN_DEFAULT_PASSWORD=admin \
  -p 5050:80 \
  dpage/pgadmin4

# Step 4: Create testdb (wait for Postgres to be ready first)
echo "⏳ Waiting for PostgreSQL to be ready..."
sleep 5  # Simple wait (for production, use a proper health check)

echo "🛠 Creating testdb..."
docker exec -it mypostgres psql -U postgres -c "CREATE DATABASE testdb;"

# Step 5: Final message
echo "✅ Setup Complete!"
echo "🔗 Access pgAdmin4 at: http://localhost:5050"
echo "   Email: admin@gmail.com"
echo "   Password: admin"
echo "📦 PostgreSQL database 'testdb' created"