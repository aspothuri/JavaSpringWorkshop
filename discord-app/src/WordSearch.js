import React, { useState } from "react";
import axios from "axios";

const WordSearch = () => {
  const [word, setWord] = useState("");
  const [results, setResults] = useState([]);

  const handleSearch = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/messages/${word}`);
      setResults(Object.entries(response.data));
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };

  return (
    <div>
      <h1>Word Usage Tracker</h1>
      <input
        type="text"
        value={word}
        onChange={(e) => setWord(e.target.value)}
        placeholder="Enter a word"
      />
      <button onClick={handleSearch}>Search</button>
      <ul>
        {results.map(([username, count]) => (
          <li key={username}>
            {username}: {count}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default WordSearch;
