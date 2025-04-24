// app/bakery-items/page.js bakery listing
'use client';
import { useEffect, useState } from 'react';

export default function BakeryItemsPage() {
  const [items, setItems] = useState([]);

  //dummy data, list sql stuff later
  useEffect(() => {
    //simulate data fetch, but later acc fetch from msyql
    const dummyData = [
      { id: 1, name: 'Croissant', price: 3.5 },
      { id: 2, name: 'Baguette', price: 2.75 },
      { id: 3, name: 'Muffin', price: 2.0 }
    ];
    setItems(dummyData);
  }, []);

  return (
    <main style={{ padding: '2rem' }}>
      <h1>Bakery Items</h1>
      <ul>
        {items.map(item => (
          <li key={item.id}>
            {item.name} — ${item.price.toFixed(2)}
          </li>
        ))}
      </ul>
    </main>
  );
}
