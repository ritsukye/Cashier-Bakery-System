// app/page.js home page
'use client';
import { useRouter } from 'next/navigation';

export default function HomePage() {
  const router = useRouter();

  const goToBakeryItems = () => {
    router.push('/bakery-items');
    //later this button will  changed
  };

  return (
    <main style={{ textAlign: 'center', marginTop: '3rem' }}>
      <h1>Welcome to the Cashier Bakery System</h1>
      <p>Manage orders, view inventory, and more.</p>
      <button
        onClick={goToBakeryItems}
        style={{
          padding: '10px 20px',
          fontSize: '16px',
          cursor: 'pointer',
          marginTop: '20px'
        }}
      >
        View Bakery Items
      </button>
    </main>
  );
}
