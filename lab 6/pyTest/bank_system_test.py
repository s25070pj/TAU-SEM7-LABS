import pytest
import asyncio
from bank_system import Account, Bank, InsufficientFundsError


@pytest.fixture
def bank():
    return Bank()


@pytest.fixture
def account1(bank):
    return bank.create_account("12345", "Jan Kowalski", 1000.0)


@pytest.fixture
def account2(bank):
    return bank.create_account("67890", "Anna Nowak", 500.0)


@pytest.mark.asyncio
def test_deposit(account1):
    account1.deposit(500.0)
    assert account1.balance == 1500.0

@pytest.mark.asyncio
def test_withdraw(account1):
    account1.withdraw(300.0)
    assert account1.balance == 700.0

@pytest.mark.asyncio
def test_withdraw_insufficient_funds(account1):
    with pytest.raises(InsufficientFundsError):
        account1.withdraw(1500.0)


@pytest.mark.asyncio
async def test_transfer(account1, account2):
    # Transfer z konta1 na konto2
    await account1.transfer(account2, 300.0)
    assert account1.balance == 700.0
    assert account2.balance == 800.0


@pytest.mark.asyncio
async def test_transfer_insufficient_funds(account1, account2):
    with pytest.raises(InsufficientFundsError):
        await account1.transfer(account2, 1500.0)


# Testy dla klasy Bank
def test_create_account(bank):
    account = bank.create_account("12345", "Joe Doe", 300.0)
    assert account.account_number == "12345"
    assert account.balance == 300.0


def test_get_account(bank, account1):
    fetched_account = bank.get_account("12345")
    assert fetched_account == account1


def test_get_account_nonexistent(bank):
    with pytest.raises(ValueError, match="Account with this id already exists"):
        bank.get_account("99999")


@pytest.mark.asyncio
async def test_process_transaction(bank, account1, account2):
    async def transaction_func():
        await account1.transfer(account2, 200.0)

    await bank.process_transaction(transaction_func)
    assert account1.balance == 800.0
    assert account2.balance == 700.0


# Testy wyjątków
def test_insufficient_funds(account1):
    with pytest.raises(InsufficientFundsError):
        account1.withdraw(1500.0)


def test_invalid_account_number(bank):
    # Tworzymy konto
    bank.create_account("12345", "Jan Kowalski", 1000.0)

    with pytest.raises(ValueError, match="Account with this id already exists"):
        bank.create_account("12345", "Karol karol", 500.0)


from unittest.mock import patch


def mock_external_authorization(*args, **kwargs):
    return True


from unittest.mock import patch


def mock_external_authorization(*args, **kwargs):
    return True


@patch('bank_system.Account.transfer', side_effect=mock_external_authorization)
def test_transfer_with_mocking(mock_transfer, account1, account2):
    account1.deposit(1000.0)
    account2.deposit(500.0)

    account1.transfer(account2, 300.0)

    assert account1.balance == 700.0
    assert account2.balance == 800.0

    mock_transfer.assert_called_once()


