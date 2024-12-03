import React, { useState } from "react";
import axios from "axios";
import "./App.css";

const App = () => {
  const [word, setWord] = useState("");
  const [results, setResults] = useState([]);
  const [error, setError] = useState("");

  const handleSearch = async () => {
    setError("");
    if (!word.trim()) {
      setError("Please enter a valid word.");
      return;
    }

    try {
      const response = await axios.get(`http://localhost:8080/messages/${word}`);
      setResults(Object.entries(response.data));
    } catch (err) {
      setError("Failed to fetch data. Please check your server or try again.");
    }
  };

  return (
    <div className="app-container">
      <header className="header">
        <h1>Word Frequency Tracker</h1>
      </header>
      <main className="main-content">
        <div className="search-container">
          <input
            type="text"
            value={word}
            onChange={(e) => setWord(e.target.value)}
            placeholder="Enter a word to search"
            className="search-input"
          />
          <button onClick={handleSearch} className="search-button">
            Search
          </button>
        </div>
        {error && <p className="error-message">{error}</p>}
        <div className="results-container">
          {results.length > 0 ? (
            <ul>
              {results.map(([username, count]) => (
                <li key={username}>
                  <span className="username">{username}</span>:{" "}
                  <span className="count">{count}</span>
                </li>
              ))}
            </ul>
          ) : (
            !error && <p>No results to display. Try searching for a word!</p>
          )}
        </div>
      </main>
    </div>
  );
};

export default App;
