import { useState } from "react";

type User = {
  firstName: string;
  lastName: string;
};

const Sample = () => {
  const [user, setUser] = useState<User | null>(null);
  const [isLoading, setIsLoading] = useState(false);

  const handleClick = async () => {
    setIsLoading(true);
    const res = await fetch("http://dummyjson.com/users/1");
    const user = await res.json();
    console.log(user);
    setUser(user);
    setIsLoading(false);
  };

  if (isLoading) return <div>Loading...</div>;

  const userName = user ? `${user.firstName} ${user.lastName}` : "Guest";

  return (
    <div>
      <h1>Hello, {userName}</h1>
      <button onClick={handleClick}>Get User</button>
    </div>
  );
};

export default Sample;
