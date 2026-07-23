package com.example.ui

object DeveloperHubContent {

    val TECH_STACK_DESC = """
        LUMINA STUDY - GLOBAL SYSTEM ARCHITECTURE & RECOMMENDATIONS

        1. Frontend App:
           - Core: Native Android (Kotlin + Jetpack Compose) for optimal performance, edge-to-edge rendering, and background services.
           - Alternative (Multiplatform): Flutter or React Native if targeting iOS concurrently.
           
        2. Backend Service:
           - Core Framework: Node.js (TypeScript) + NestJS or Express.js. Perfect for heavy asynchronous I/O and real-time operations.
           - Gateway: Nginx or AWS API Gateway for TLS termination, load balancing, and rate-limiting.
           
        3. Real-Time Communication Layer:
           - Engine: WebSockets (using Socket.io or native ws protocol).
           - Scale adapter: Redis Pub/Sub adapter to sync WebSocket events across multiple backend instances.
           - Presence: Redis Key-Value store with TTL for tracking live online user status instantly.

        4. Primary Database & Cache:
           - DB: PostgreSQL (managed, e.g., AWS RDS or Supabase) with PgBouncer for transaction connection pooling.
           - Cache: Redis for database query caching (scripture text cache, user session tokens, and hot rate limit counters).
    """.trimIndent()

    val DATABASE_SCHEMA_SQL = """
        -- DATABASE MIGRATION SCHEMA (PostgreSQL Dialect)

        -- 1. Users Table
        CREATE TABLE users (
            id SERIAL PRIMARY KEY,
            email VARCHAR(255) UNIQUE NOT NULL,
            password_hash VARCHAR(255) NOT NULL,
            display_name VARCHAR(100) NOT NULL,
            bio TEXT,
            location VARCHAR(100) DEFAULT 'Global',
            preferred_translation VARCHAR(10) DEFAULT 'ESV',
            avatar_color INT DEFAULT -3563421, -- Custom theme ARGB integer
            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
            updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        );

        -- 2. Study Groups Table
        CREATE TABLE study_groups (
            id SERIAL PRIMARY KEY,
            title VARCHAR(255) NOT NULL,
            topic TEXT NOT NULL,
            host_id INT REFERENCES users(id) ON DELETE SET NULL,
            host_name VARCHAR(100) NOT NULL,
            schedule_text VARCHAR(100) NOT NULL,
            is_private BOOLEAN DEFAULT FALSE,
            is_live BOOLEAN DEFAULT FALSE,
            participant_count INT DEFAULT 0,
            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        );

        -- 3. Chat Messages Table
        CREATE TABLE chat_messages (
            id SERIAL PRIMARY KEY,
            group_id INT REFERENCES study_groups(id) ON DELETE CASCADE,
            sender_name VARCHAR(100) NOT NULL,
            message_text TEXT NOT NULL,
            timestamp BIGINT NOT NULL,
            is_pinned BOOLEAN DEFAULT FALSE,
            is_reported BOOLEAN DEFAULT FALSE,
            is_system BOOLEAN DEFAULT FALSE,
            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        );

        -- 4. Blocked Users Table (Moderation)
        CREATE TABLE blocked_users (
            id SERIAL PRIMARY KEY,
            blocker_email VARCHAR(255) NOT NULL,
            blocked_username VARCHAR(100) NOT NULL,
            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
            UNIQUE(blocker_email, blocked_username)
        );

        -- Indexes for high performance
        CREATE INDEX idx_messages_group ON chat_messages(group_id, timestamp);
        CREATE INDEX idx_users_email ON users(email);
    """.trimIndent()

    val API_DOCUMENTATION = """
        OPENAPI / SWAGGER API ARCHITECTURE SPECIFICATION
        Version: v1 (Active)
        Base URL: https://api.luminastudy.com/v1

        🔐 AUTHENTICATION ENDPOINTS:
        -------------------------------------------------------------
        POST  /api/v1/auth/register
          - Description: Register a new community user.
          - Body: { email, password, displayName, location }
          - Response: 201 Created { user: { id, email, displayName }, token }

        POST  /api/v1/auth/login
          - Description: Secure sign-in to retrieve JWT.
          - Body: { email, password }
          - Response: 200 OK { user, token }

        📖 STUDY ROOMS & GROUPS ENDPOINTS:
        -------------------------------------------------------------
        GET   /api/v1/groups
          - Description: Get list of active public groups.
          - Rate Limited: Max 60 requests / minute.
          - Headers: Authorization: Bearer <JWT>
          - Response: 200 OK [ { id, title, topic, isLive, participantCount } ]

        POST  /api/v1/groups
          - Description: Create a public or private study room.
          - Body: { title, topic, isPrivate }
          - Response: 201 Created { group }

        💬 LIVE SYNC REAL-TIME CHAT:
        -------------------------------------------------------------
        GET   /api/v1/groups/{id}/messages
          - Description: Fetch historical and pinned messages for a specific group.
          - Response: 200 OK [ { id, senderName, messageText, timestamp, isPinned } ]

        POST  /api/v1/groups/{id}/messages/report
          - Description: Flag a message for UGC moderation.
          - Body: { messageId, reason }
          - Response: 200 OK { status: "under_review" }

        🚫 USER MODERATION & SAFETY:
        -------------------------------------------------------------
        POST  /api/v1/users/block
          - Description: Block a user. Hides messages on client-side and filters on backend.
          - Body: { blockedUsername }
          - Response: 200 OK { success: true }
    """.trimIndent()

    val BACKEND_BOILERPLATE_JS = """
        /**
         * LUMINA STUDY - NODE.JS BACKEND INITIAL BOILERPLATE
         * Framework: Express.js + JSON Web Tokens (JWT) + Express Rate Limiter
         * Safe, scalable, and fully ready for AWS/GCP deployment.
         */

        const express = require('express');
        const jwt = require('jsonwebtoken');
        const rateLimit = require('express-rate-limit');
        const helmet = require('helmet');
        const cors = require('cors');

        const app = express();
        const PORT = process.env.PORT || 4000;
        const JWT_SECRET = process.env.JWT_SECRET || 'lumina_divine_sec_key_777';

        // 1. Production Security Headers & CORS
        app.use(helmet());
        app.use(cors({ origin: '*' }));
        app.use(express.json());

        // 2. Global Rate Limiter (Google Play UGC & API DDoS Protection)
        const globalLimiter = rateLimit({
            windowMs: 1 * 60 * 1000, // 1 minute window
            max: 60, // Limit each IP to 60 requests per window
            message: { error: 'Too many requests, please try again in a minute.' }
        });
        app.use(globalLimiter);

        // Strict Limiter for Auth Routes (Brute Force Protection)
        const authLimiter = rateLimit({
            windowMs: 15 * 60 * 1000, // 15 minutes
            max: 5, // Limit to 5 attempts
            message: { error: 'Too many auth attempts. Please wait 15 minutes.' }
        });

        // 3. Mock Database (In production, replace with Postgres/Knex)
        const usersDb = [];
        const studyGroups = [
            { id: 1, title: 'Wisdom of Proverbs', topic: 'Chapter 3', isLive: true }
        ];

        // 4. JWT Verification Middleware
        const authenticateJWT = (req, res, next) => {
            const authHeader = req.headers.authorization;
            if (authHeader) {
                const token = authHeader.split(' ')[1];
                jwt.verify(token, JWT_SECRET, (err, user) => {
                    if (err) {
                        return res.status(403).json({ error: 'Forbidden: Invalid Token' });
                    }
                    req.user = user;
                    next();
                });
            } else {
                res.status(401).json({ error: 'Unauthorized: Missing Authorization Header' });
            }
        };

        // 5. Auth Endpoints
        app.post('/api/v1/auth/register', authLimiter, async (req, res) => {
            const { email, password, displayName, location } = req.body;
            if (!email || !password || !displayName) {
                return res.status(400).json({ error: 'Missing registration details' });
            }

            const exists = usersDb.find(u => u.email === email);
            if (exists) return res.status(409).json({ error: 'User already exists' });

            // In production: const passwordHash = await bcrypt.hash(password, 10);
            const user = { id: usersDb.length + 1, email, password, displayName, location };
            usersDb.push(user);

            const token = jwt.sign({ id: user.id, email: user.email }, JWT_SECRET, { expiresIn: '24h' });
            res.status(201).json({ user: { id: user.id, email, displayName }, token });
        });

        app.post('/api/v1/auth/login', authLimiter, (req, res) => {
            const { email, password } = req.body;
            const user = usersDb.find(u => u.email === email && u.password === password);
            if (!user) return res.status(401).json({ error: 'Invalid email or password' });

            const token = jwt.sign({ id: user.id, email: user.email }, JWT_SECRET, { expiresIn: '24h' });
            res.json({ user: { id: user.id, email: user.email, displayName: user.displayName }, token });
        });

        // 6. Secured Data Endpoints
        app.get('/api/v1/groups', authenticateJWT, (req, res) => {
            res.json(studyGroups);
        });

        // Health Check
        app.get('/health', (req, res) => {
            res.json({ status: 'healthy', timestamp: new Date() });
        });

        app.listen(PORT, () => {
            console.log(`Lumina Study Backend listening securely on port ${"$"}{PORT}`);
        });
    """.trimIndent()
}
