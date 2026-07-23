const express = require('express');
const fetch = global.fetch || require('node-fetch');
const app = express();
app.use(express.json());

// Proxy to Bible API
app.get('/:passage', async (req, res) => {
  try {
    const passage = req.params.passage;
    const translation = req.query.translation;
    const url = new URL(`https://bible-api.com/${passage}`);
    if (translation) url.searchParams.set('translation', translation);
    const r = await fetch(url.toString());
    const json = await r.json();
    res.json(json);
  } catch (err) {
    res.status(502).json({ error: 'upstream error', details: err.message });
  }
});

// Mock Gemini generation endpoint
app.post('/v1beta/models/gemini-3.5-flash:generateContent', (req, res) => {
  const body = req.body || {};
  // Return a simple mock response matching the schema
  const candidate = {
    content: {
      parts: [
        { text: 'This is a generated sample based on your request.' }
      ]
    }
  };
  res.json({ candidates: [candidate] });
});

const port = process.env.PORT || 8080;
app.listen(port, () => console.log(`OpenAPI stub server listening on ${port}`));
