FROM python:3.10-slim

WORKDIR /app

RUN apt-get update && apt-get install -y \
    mariadb-client \
    libmariadb-dev \
    gcc \
    && apt-get clean && rm -rf /var/lib/apt/lists/*

COPY . .

RUN pip install --no-cache-dir -r requirements.txt

EXPOSE 50051
EXPOSE 8082

CMD ["python", "main.py"]